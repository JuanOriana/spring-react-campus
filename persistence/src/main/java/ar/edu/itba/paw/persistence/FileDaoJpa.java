package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.*;

@Primary
@Repository
public class FileDaoJpa extends BasePaginationDaoImpl<FileModel> implements FileDao {

    private static final Long NO_COURSE = -1L;

    @Autowired
    private FileCategoryDao fileCategoryDao;

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
        TypedQuery<FileExtension> fileExtensionQuery = em.createQuery("SELECT fe FROM FileExtension fe WHERE fe.fileExtension = :fileExtensionName", FileExtension.class);
        fileExtensionQuery.setParameter("fileExtensionName", fileExtension);
        List<FileExtension> resultsFileExtensionQuery = fileExtensionQuery.getResultList();
        if (resultsFileExtensionQuery.isEmpty()){
            fileExtension = "other";
        } else {
            fileExtension = resultsFileExtensionQuery.get(0).getFileExtensionName();
        }
        fileExtensionQuery = em.createQuery("SELECT fe FROM FileExtension fe WHERE fe.fileExtension = :fileExtensionName", FileExtension.class);
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


    @Transactional
    @Override
    public boolean associateCategory(Long fileId, Long fileCategoryId) {
        Optional<FileModel> optionalDBFile = findById(fileId);
        Optional<FileCategory> optionalFileCategory = fileCategoryDao.findById(fileCategoryId);
        if(!optionalDBFile.isPresent() || !optionalFileCategory.isPresent()) return false;
        List<FileCategory> dbFile = optionalDBFile.get().getCategories();
        for (FileCategory fileCategory : dbFile) if (fileCategory.getCategoryId() == fileCategoryId) return false;
        dbFile.add(optionalFileCategory.get());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileCategory> getFileCategories(Long fileId) {
        Optional<FileModel> optionalFileModel = findById(fileId);
        if(!optionalFileModel.isPresent()) return Collections.emptyList();
        return optionalFileModel.get().getCategories();
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
        queryHasAccess.setParameter("fileId", fileId);
        queryHasAccess.setParameter("userId", userId);
        return !queryHasAccess.getResultList().isEmpty();
    }

    @Transactional(readOnly = true)
    @Override
    public CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions, List<Long> categories, Long userId, Long courseId, CampusPageRequest pageRequest, CampusPageSort sort) throws PaginationArgumentException {
        return findFileByPage(keyword, extensions, categories, userId, courseId, pageRequest, sort);
    }

    @Override
    public CampusPage<FileModel> listByUser(String keyword, List<Long> extensions, List<Long> categories, Long userId, CampusPageRequest pageRequest, CampusPageSort sort) throws PaginationArgumentException {
        return findFileByPage(keyword, extensions, categories, userId, NO_COURSE, pageRequest, sort);
    }

    @Transactional
    @Override
    public void incrementDownloads(Long fileId) {
        Optional<FileModel> optionalFileModel = findById(fileId);
        if (!optionalFileModel.isPresent()) return;
        FileModel file = optionalFileModel.get();
        file.setDownloads(file.getDownloads() + 1);
    }

    private CampusPage<FileModel> findFileByPage(String keyword, List<Long> extensions, List<Long> categories,
                                                 Long userId, Long courseId, CampusPageRequest pageRequest,
                                                 CampusPageSort sort) {
        String unOrderedQuery = buildFilteredQuery(extensions, categories, courseId);
        Map<String, Object> properties = new HashMap<>();
        if(!extensions.isEmpty()) properties.put("extensionIds", extensions);
        if(!categories.isEmpty()) properties.put("categoryIds", categories);
        properties.put("query", "%" + keyword + "%");
        if(!courseId.equals(NO_COURSE)) {
            properties.put("courseId", courseId);
        } else {
            properties.put("userId", userId);
        }
        String orderedQuery = unOrderedQuery + " ORDER BY " + sort.getProperty() + " " + sort.getDirection();
        String mappingQuery = "SELECT f FROM FileModel f WHERE f.fileId IN (:ids) ORDER BY f." + sort.getProperty() + " " + sort.getDirection();
        return listBy(properties, orderedQuery, mappingQuery, pageRequest, FileModel.class);
    }


    private String buildFilteredQuery(List<Long> extensions, List<Long> categories, Long courseId) {
        StringBuilder query = new StringBuilder();
        if(!extensions.isEmpty()) query.append("AND ( fileExtensionId IN ( :extensionIds ) ) ");
        if(!categories.isEmpty()) query.append("AND ( categoryId IN ( :categoryIds ) )");
        String courseSelection = courseId < 0 ? "(SELECT courseId FROM user_to_course WHERE userId = :userId)" : "(:courseId)";
        return  "SELECT fileId " +
                "FROM files NATURAL JOIN category_file_relationship " +
                "WHERE fileName ILIKE :query AND courseId IN " + courseSelection + " " + query;
    }

}
