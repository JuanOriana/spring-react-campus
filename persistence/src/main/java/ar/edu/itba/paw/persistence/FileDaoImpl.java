package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class  FileDaoImpl implements FileDao {

    private static final int MIN_PAGE_COUNT = 1;
    private static final int MIN_PAGE_SIZE = 1;
    private static final int MAX_PAGE_SIZE = 50;

    @Autowired
    private FileExtensionDao fileExtensionDao;

    @Autowired
    private FileCategoryDao fileCategoryDao;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertCategory;
    private static final RowMapper<FileModel> FILE_MODEL_ROW_MAPPER = (rs, rowNum) ->

            new FileModel.Builder()
                    .withFileId(rs.getLong("fileId"))
                    .withSize(rs.getLong("fileSize"))
                    .withName(rs.getString("fileName"))
                    .withDate(rs.getTimestamp("fileDate").toLocalDateTime())
                    .withFile(rs.getBytes("file"))
                    .withExtension(new FileExtension(rs.getLong("fileExtensionId"), rs.getString("fileExtension")))
                    .withDownloads(rs.getLong("downloads"))
                    .withCourse(new Course.Builder()
                            .withCourseId(rs.getLong("courseId"))
                            .withYear(rs.getInt("year"))
                            .withQuarter(rs.getInt("quarter"))
                            .withBoard(rs.getString("board"))
                            .withSubject(new Subject(rs.getLong("subjectId"), rs.getString("code"),
                                    rs.getString("subjectName")))
                            .build())
                    .build();

    private static final RowMapper<FileExtension> FILE_EXTENSION_ROW_MAPPER = (rs, rowNum) ->
        new FileExtension(rs.getLong("fileExtensionId"), rs.getString("fileExtension"));

    private static final RowMapper<FileCategory> FILE_CATEGORY_ROW_MAPPER = (rs, rowNum) ->
        new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName"));

    @Autowired
    public FileDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files").usingGeneratedKeyColumns("fileid");
        jdbcInsertCategory = new SimpleJdbcInsert(jdbcTemplate).withTableName("category_file_relationship");
    }

    @Override
    public FileModel create(Long size, LocalDateTime date, String name, byte[] file, Course course) {
        String fileExtension = getExtension(name);
        FileExtension fileExtensionModel;
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM file_extensions WHERE fileExtension = ?", new Object[]{fileExtension}, Integer.class);
        if (count == 0) fileExtension = "other";
        List<FileExtension> list = jdbcTemplate.query("SELECT fileExtensionId, fileExtension FROM file_extensions WHERE fileExtension = ?", new Object[]{fileExtension}, FILE_EXTENSION_ROW_MAPPER);
        fileExtensionModel = list.get(0);
        final Map<String, Object> args = new HashMap<>();
        args.put("fileSize", size);
        args.put("file", file);
        args.put("fileName", name);
        args.put("downloads",0);
        args.put("fileDate", Timestamp.valueOf(date));
        args.put("fileExtensionId", fileExtensionModel.getFileExtensionId());
        args.put("courseId", course.getCourseId());
        final Long fileId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new FileModel.Builder()
                .withFileId(fileId)
                .withSize(size)
                .withName(name)
                .withDate(date)
                .withFile(file)
                .withExtension(fileExtensionModel)
                .withCourse(course)
                .withDownloads(0L)
                .build();
    }

    @Override
    public boolean update(Long fileId, FileModel file) {
        return jdbcTemplate.update("UPDATE files " +
                "SET file = ?," +
                "fileName = ?," +
                "fileSize = ?," +
                "fileDate = ?," +
                "fileExtensionId = ?," +
                "courseId = ?, " +
                "downloads = ? " +
                "WHERE fileId = ?", file.getFile(), file.getName(), file.getFile().length,
                Timestamp.valueOf(LocalDateTime.now()), file.getExtension().getFileExtensionId(),
                file.getCourse().getCourseId(), file.getDownloads(), fileId) == 1;
    }

    @Override
    public boolean delete(Long fileId) {
        return jdbcTemplate.update("DELETE FROM files WHERE fileId = ?", fileId) == 1;
    }

    @Override
    public List<FileModel> list(Long userId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, " +
                "quarter, board, subjectId, code, subjectName, downloads " +
                "FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN user_to_course " +
                "WHERE userId = ?", new Object[]{userId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<FileModel> getById(Long fileId) {
        return jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, " +
                        "fileExtension, courseId, year, quarter, board, subjectId, code, subjectName, downloads " +
                        "FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileId = ?",
                new Object[]{fileId}, FILE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<FileModel> getByName(String fileName) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, " +
                        "fileExtensionId, fileExtension, courseId, year, quarter, board, " +
                        "subjectId, code, subjectName, downloads " +
                        "FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileName = ?",
                new Object[]{fileName}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByExtension(Long extensionId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, " +
                "fileExtensionId, fileExtension, courseId, year, " +
                "quarter, board, subjectId, code, subjectName, downloads " +
                "FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects " +
                "WHERE fileExtensionId = ?", new Object[]{extensionId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByExtension(String extension) {
        String fileExtension = extension;
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM file_extensions WHERE fileExtension = ?",
                new Object[]{fileExtension}, Integer.class);
        if (count == 0) {
            fileExtension = "other";
        }
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, " +
                        "fileExtensionId, fileExtension, courseId, year, " +
                        "quarter, board, subjectId, code, subjectName, downloads " +
                        "FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileExtension = ?",
                new Object[]{fileExtension}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public boolean addCategory(Long fileId, Long fileCategoryId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM category_file_relationship WHERE fileId = ? AND categoryId = ?", new Object[]{fileId, fileCategoryId}, Integer.class);
        if (count == 0) {
            final Map<String, Object> args = new HashMap<>();
            args.put("fileId", fileId);
            args.put("categoryId", fileCategoryId);
            jdbcInsertCategory.execute(args);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCategory(Long fileId, Long fileCategoryId) {
        return jdbcTemplate.update("DELETE FROM category_file_relationship WHERE fileId = ? AND categoryId = ?", fileId, fileCategoryId) == 1;
    }

    @Override
    public List<FileCategory> getFileCategories(Long fileId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT categoryId, categoryName FROM category_file_relationship NATURAL JOIN file_categories WHERE fileId = ?", new Object[]{fileId}, FILE_CATEGORY_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByCategory(Long fileCategoryId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, " +
                "fileExtensionId, fileExtension, courseId, year, " +
                "quarter, board, subjectId, code, subjectName, downloads " +
                "FROM files NATURAL JOIN category_file_relationship NATURAL JOIN file_extensions NATURAL JOIN courses " +
                "NATURAL JOIN subjects WHERE categoryId = ?", new Object[]{fileCategoryId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByCourseId(Long courseId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, " +
                        "fileExtensionId, fileExtension, courseId, year, " +
                        "quarter, board, subjectId, code, subjectName, downloads " +
                        "FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE courseId = ?",
                new Object[]{courseId}, FILE_MODEL_ROW_MAPPER));
    }

    private RowMapper<Integer> ACCESS_ROW_MAPPER = ((rs, rowNum) -> rs.getInt("userId"));

    @Override
    public boolean hasAccess(Long fileId, Long userId) {
        Optional<Integer> userResponse = jdbcTemplate.query("SELECT * FROM files NATURAL JOIN user_to_course WHERE " +
                "fileId = ? AND userId = ?", new Object[]{fileId, userId}, ACCESS_ROW_MAPPER).stream().findFirst();
        return userResponse.isPresent();
    }

    @Override
    public CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions,
                                              List<Long> categories, Long userId, Long courseId,
                                              CampusPageRequest pageRequest,
                                              CampusPageSort sort) throws PaginationArgumentException {

        return findFileByPage(keyword, extensions, categories, userId, courseId, pageRequest, sort);
    }

    @Override
    public CampusPage<FileModel> listByUser(String keyword, List<Long> extensions,
                                            List<Long> categories, Long userId,
                                            CampusPageRequest pageRequest,
                                            CampusPageSort sort) throws PaginationArgumentException {
        return findFileByPage(keyword, extensions, categories, userId, -1L, pageRequest, sort);
    }

    private String getExtension(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }

    private String buildFilteredQuery(List<Long> extensions, List<Long> categories,
                                      List<Object> params, Long courseId) {
        StringBuilder query = new StringBuilder();

        if (!extensions.isEmpty()) {
            query.append(" ( ");
            for (Long extension : extensions) {
                Optional<java.lang.String> extensionName = fileExtensionDao.getExtension(extension);
                params.add(extensionName.orElse("other"));
                query.append(" fileExtension = ? OR ");
            }
            query.delete(query.length() - 4, query.length());
            query.append(" ) ");
        }

        if (!categories.isEmpty()) {
            if (!extensions.isEmpty()) {
                query.append(" AND ");
            }
            query.append(" ( ");
            for (Long category : categories) {
                Optional<String> cat = fileCategoryDao.getCategory(category);
                params.add(cat.orElse("none"));
                query.append("categoryname = ? OR ");
            }
            query.delete(query.length() - 4, query.length());
            query.append(" ) ");
        }
        String courseSelection = courseId < 0 ? "(SELECT courseId FROM user_to_course WHERE userId = ?)" : "(?)";
        String selectByName = "SELECT * FROM files NATURAL JOIN courses NATURAL JOIN file_extensions NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories WHERE LOWER(fileName) LIKE ? AND courseId IN " + courseSelection;
        String selectFilterExtensionsAndCategory = "";

        if (!extensions.isEmpty() || !categories.isEmpty()) {
            selectFilterExtensionsAndCategory = " INTERSECT SELECT * FROM files NATURAL JOIN courses NATURAL JOIN file_extensions  NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories WHERE " + query;
        }
        return selectByName + selectFilterExtensionsAndCategory;
    }

    private Object[] getQueryParams(List<Object> params, String keyword, Long courseId, Long userId) {
        Object[] sqlParams = new Object[params.size() + 2];
        sqlParams[0] = "%" + keyword.toLowerCase() + "%";
        sqlParams[1] = courseId < 0 ? userId : courseId;
        for (int i = 0; i < params.size(); i++) {
            sqlParams[i + 2] = params.get(i);
        }
        return sqlParams;
    }


    private CampusPage<FileModel> findFileByPage(String keyword, List<Long> extensions, List<Long> categories,
                                          Long userId, Long courseId, CampusPageRequest pageRequest,
                                          CampusPageSort sort) {
        List<Object> params = new ArrayList<>();
        String unOrderedQuery = buildFilteredQuery(extensions, categories, params, courseId);
        Object[] sqlParams = getQueryParams(params, keyword, courseId, userId);
        int pageCount = getPageCount(unOrderedQuery, sqlParams, pageRequest.getPageSize());
        if(pageCount == 0) return new CampusPage<>();
        if(pageRequest.getPage() > pageCount) throw new PaginationArgumentException();
        List<FileModel> files = jdbcTemplate.query(unOrderedQuery + " " +
                        "ORDER BY " + sort.getProperty() + " " + sort.getDirection() + " " +
                        "LIMIT " + pageRequest.getPageSize() + " OFFSET " + (pageRequest.getPage() - 1) * pageRequest.getPageSize(),
                sqlParams, FILE_MODEL_ROW_MAPPER);
        return new CampusPage<>(files, pageRequest.getPageSize(), pageRequest.getPage(), pageCount);
    }

    @Override
    public void incrementDownloads(Long fileId){
        jdbcTemplate.update("UPDATE files SET downloads = downloads + 1 WHERE fileId = ?", fileId);
    }

    private Integer getPageCount(String unOrderedQuery, Object[] sqlParams, Integer pageSize) {
        return (int) Math.ceil((double)getPageRowCount(unOrderedQuery, sqlParams) / pageSize);
    }

    private int getPageRowCount(String rowCountSql, Object[] args) {
        return jdbcTemplate.queryForObject(
                "SELECT count(1) AS row_count FROM (" + rowCountSql + ") as foo",
                args, (rs, rowNum) -> rs.getInt(1)
        );
    }

}
