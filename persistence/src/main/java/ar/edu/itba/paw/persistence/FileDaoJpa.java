package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class FileDaoJpa implements FileDao {

    @PersistenceContext
    private EntityManager em;

    private String getExtension(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }

    @Transactional
    @Override
    public FileModel create(Long size, LocalDateTime date, String name, byte[] file, Course course) {
        String fileExtension = getExtension(name);
        TypedQuery<FileExtension> fileExtensionQuery = em.createQuery("SELECT fe FROM FileExtension fe WHERE fe.fileExtensionName = :fileExtensionName", FileExtension.class);
        fileExtensionQuery.setParameter("fileExtensionName", fileExtension);
        List<FileExtension> resultsFileExtensionQuery = fileExtensionQuery.getResultList();
        if (resultsFileExtensionQuery.size() == 0){
            fileExtension = "other";
        }else{
            fileExtension = resultsFileExtensionQuery.get(0).getFileExtensionName();
        }
        fileExtensionQuery = em.createQuery("SELECT fe.fileExtensionId FROM FileExtension fe WHERE fe.fileExtensionName = :fileExtensionName", FileExtension.class);
        fileExtensionQuery.setParameter("fileExtensionName", fileExtension);
        final FileModel fileModel = new FileModel.Builder()
                .withSize(size)
                .withFile(file)
                .withName(name)
                .withDownloads(0L)
                .withDate(LocalDateTime.now())
                .withExtension(fileExtensionQuery.getResultList().get(0))
                .withCourse(course)
                .build();
        em.persist(fileModel);
        return fileModel;
    }

    @Transactional
    @Override
    public boolean update(Long fileId, FileModel file) {
        Optional<FileModel> dbFile = findById(fileId);
        if (!dbFile.isPresent()) return false;
        dbFile.get().merge(file);
        em.flush();
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long fileId) {
        Optional<FileModel> dbFile = findById(fileId);
        if (!dbFile.isPresent()) return false;
        em.remove(dbFile.get());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileModel> list(Long userId) {
        TypedQuery<FileModel> listFilesOfUser = em.createQuery("SELECT f FROM FileModel f JOIN Enrollment e WHERE e.user.userId = :userId", FileModel.class);
        listFilesOfUser.setParameter("userId", userId);
        return listFilesOfUser.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FileModel> findById(Long fileId) {
        return Optional.ofNullable(em.find(FileModel.class, fileId));
    }

    @Override
    public boolean associateCategory(Long fileId, Long fileCategoryId) {
        TypedQuery<FileModel> queryFileCategory = em.createQuery("SELECT f FROM FileModel f JOIN CategoryFileRelationship cfr WHERE f.fileId = :fileId AND cfr.fileCategory.fileCategoryId = :fileCategoryId", FileModel.class);
        queryFileCategory.setParameter("fileId", fileId);
        queryFileCategory.setParameter("fileCategoryId", fileCategoryId);
        if (queryFileCategory.getResultList().size() == 0){
            final CategoryFileRelationship categoryFileRelationship = new CategoryFileRelationship(em.find(FileCategory.class,fileCategoryId), em.find(FileModel.class,fileId));
            em.persist(categoryFileRelationship);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileCategory> getFileCategories(Long fileId) {
        TypedQuery<FileCategory> listFileCategories = em.createQuery("SELECT cfr.fileCategory FROM CategoryFileRelationship cfr WHERE cfr.fileModel.fileId = :fileId", FileCategory.class);
        listFileCategories.setParameter("fileId", fileId);
        return listFileCategories.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileModel> findByCategory(Long fileCategoryId) {
        TypedQuery<FileModel> listByCategory = em.createQuery("SELECT f FROM FileModel f JOIN FileCategory fc WHERE fc.categoryId = :categoryId", FileModel.class);
        listByCategory.setParameter("categoryId", fileCategoryId);
        return listByCategory.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileModel> findByCourseId(Long courseId) {
        TypedQuery<FileModel> listByCourse = em.createQuery("SELECT f FROM FileModel f WHERE f.course.courseId = :courseId", FileModel.class);
        listByCourse.setParameter("courseId", courseId);
        return listByCourse.getResultList();
    }

    @Override
    public boolean hasAccess(Long fileId, Long userId) {
        TypedQuery<FileModel> queryHasAccess = em.createQuery("SELECT f FROM FileModel f, Enrollment e WHERE f.course.courseId = e.course.courseId AND e.user.userId = :userId", FileModel.class);
        //queryHasAccess.setParameter("fileId", fileId);
        //TODO: ver por que el courseVoter devuelve null el fileId
        queryHasAccess.setParameter("userId", userId);
        return queryHasAccess.getResultList().size() > 0;
    }

    @Override
    public CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions, List<Long> categories, Long userId, Long courseId, CampusPageRequest pageRequest, CampusPageSort sort) throws PaginationArgumentException {
        return findFileByPage(keyword, extensions, categories, userId, courseId, pageRequest, sort);
        //return null;
    }

    @Override
    public CampusPage<FileModel> listByUser(String keyword, List<Long> extensions, List<Long> categories, Long userId, CampusPageRequest pageRequest, CampusPageSort sort) throws PaginationArgumentException {
        return findFileByPage(keyword, extensions, categories, userId, -1L, pageRequest, sort);
        //return null;
    }

    @Override
    public void incrementDownloads(Long fileId) {
        Optional<FileModel> dbFile = findById(fileId);
        if (!dbFile.isPresent()) return;
        dbFile.get().setDownloads(dbFile.get().getDownloads() + 1);
        em.flush();
    }

    private CampusPage<FileModel> findFileByPage(String keyword, List<Long> extensions, List<Long> categories,
                                                 Long userId, Long courseId, CampusPageRequest pageRequest,
                                                 CampusPageSort sort) {
        String unOrderedQuery = buildFilteredQuery(extensions, categories, courseId);
        List<Long> filterMerge = new ArrayList<>();
        filterMerge.addAll(extensions);
        filterMerge.addAll(categories);
        Object[] sqlParams = getQueryParams(filterMerge, keyword, courseId, userId);
        int pageCount = getPageCount(unOrderedQuery, sqlParams, pageRequest.getPageSize());
        if(pageCount == 0) return new CampusPage<>();
        if(pageRequest.getPage() > pageCount) throw new PaginationArgumentException();
//        List<FileModel> files = jdbcTemplate.query(unOrderedQuery + " " +
//                        "ORDER BY " + sort.getProperty() + " " + sort.getDirection() + " " +
//                        "LIMIT " + pageRequest.getPageSize() + " OFFSET " + (pageRequest.getPage() - 1) * pageRequest.getPageSize(),
//                sqlParams, FILE_PREVIEW_ROW_MAPPER);
        List<FileModel> files = em.createQuery("SELECT f FROM FileModel f WHERE f.course.courseId = :courseId", FileModel.class).setParameter("courseId", courseId).getResultList();
        return new CampusPage<>(files, pageRequest.getPageSize(), pageRequest.getPage(), pageCount);
    }

    private String buildFilteredQuery(List<Long> extensions, List<Long> categories, Long courseId) {
        StringBuilder extensionQuery = new StringBuilder();
        if(!extensions.isEmpty()) {
            extensionQuery.append("AND (");
            extensions.forEach(e -> extensionQuery.append("f.fileExtensionId = ? OR "));
            extensionQuery.delete(extensionQuery.length() - 4, extensionQuery.length());
            extensionQuery.append(")");
        }
        StringBuilder categoryQuery = new StringBuilder();
        if(!categories.isEmpty()) {
            categoryQuery.append("AND (");
            categories.forEach(c -> categoryQuery.append("f.categoryId = ? OR "));
            categoryQuery.delete(categoryQuery.length() - 4, categoryQuery.length());
            categoryQuery.append(")");
        }
        String courseSelection = courseId < 0 ? "(SELECT utc.course.courseId FROM UserToCourse utc WHERE utc.user.userId = :userId1)" : "(:courseId1)";
        return "SELECT f FROM FileModel f " +
                "WHERE LOWER(f.fileName) LIKE ? AND f.course.courseId IN " + courseSelection + " " +
                extensionQuery.toString() + " " + categoryQuery.toString();
    }

    private Object[] getQueryParams(List<Long> params, String keyword, Long courseId, Long userId) {
        Object[] sqlParams = new Object[params.size() + 2];
        sqlParams[0] = "%" + keyword.toLowerCase() + "%";
        sqlParams[1] = courseId < 0 ? userId : courseId;
        for (int i = 0; i < params.size(); i++) {
            sqlParams[i + 2] = params.get(i);
        }
        return sqlParams;
    }

    private Integer getPageCount(String unOrderedQuery, Object[] sqlParams, Integer pageSize) {
        return (int) Math.ceil((double)getPageRowCount(unOrderedQuery, sqlParams) / pageSize);
    }

    private int getPageRowCount(String rowCountSql, Object[] args) {
//        return jdbcTemplate.queryForObject(
//                "SELECT count(1) AS row_count FROM (" + rowCountSql + ") as foo",
//                args, (rs, rowNum) -> rs.getInt(1)
//        );
        return 2;
    }

}
