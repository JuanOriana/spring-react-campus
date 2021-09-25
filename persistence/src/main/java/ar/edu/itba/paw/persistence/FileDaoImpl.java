package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class FileDaoImpl implements FileDao {

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

    private String getExtension(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }

    // TODO: Refactor
    public List<FileModel> listByCriteria(OrderCriterias orderCriterias, SortCriterias criterias, String param, List<Long> extensions, List<Long> categories, Long userId, Long courseId) {

        StringBuilder extensionAndCategoryQuery = new StringBuilder();
        List<Object> params = new ArrayList<>();
        // Armado del string para filtrar por extensiones (si hay )
        if (!extensions.isEmpty()) {
            extensionAndCategoryQuery.append(" ( ");
            for (Long extension : extensions) {
                Optional<String> exten = fileExtensionDao.getExtension(extension);
                params.add(exten.orElse("other"));
                extensionAndCategoryQuery.append(" fileExtension = ? OR ");
            }
            extensionAndCategoryQuery.delete(extensionAndCategoryQuery.length() - 4, extensionAndCategoryQuery.length()); // Removed the last AND
            extensionAndCategoryQuery.append(" ) ");
        }
        // Armado del string para filtrar por categorias (si hay )
        if (!categories.isEmpty()) {
            if (!extensions.isEmpty()) {
                extensionAndCategoryQuery.append(" AND ");
            }
            extensionAndCategoryQuery.append(" ( ");
            for (Long category : categories) {
                Optional<String> cat = fileCategoryDao.getCategory(category);
                params.add(cat.orElse("none"));
                extensionAndCategoryQuery.append("categoryname = ? OR ");
            }
            extensionAndCategoryQuery.delete(extensionAndCategoryQuery.length() - 4, extensionAndCategoryQuery.length()); // Removed the last AND
            extensionAndCategoryQuery.append(" ) ");
        }


        String courseSelection = courseId < 0 ? "(SELECT courseId FROM user_to_course WHERE userId = ?)" : "(?)";
        Long courseSelectionParam = courseId < 0 ? userId : courseId;
        String selectByName = "SELECT * FROM files NATURAL JOIN courses NATURAL JOIN file_extensions NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories WHERE LOWER(fileName) LIKE ? AND courseId IN " + courseSelection;
        String selectFilterExtensionsAndCategory;

        if (extensions.isEmpty() && categories.isEmpty()) {
            selectFilterExtensionsAndCategory = "";
        } else {
            selectFilterExtensionsAndCategory = " INTERSECT SELECT * FROM files NATURAL JOIN courses NATURAL JOIN file_extensions  NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories WHERE " + extensionAndCategoryQuery;
        }
        String selectAll = "SELECT *  FROM files NATURAL JOIN courses NATURAL JOIN file_extensions  NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories WHERE courseId IN " + courseSelection;

        Object[] sqlParams = new Object[params.size() + 2];
        sqlParams[0] = "%" + param.toLowerCase() + "%";
        sqlParams[1] = courseSelectionParam;
        for (int i = 0; i < params.size(); i++) {
            sqlParams[i + 2] = params.get(i);
        }

        switch (criterias) {
            case NAME:
                return new ArrayList<>(jdbcTemplate.query(selectByName + selectFilterExtensionsAndCategory + " ORDER BY fileName " + orderCriterias.getValue(), sqlParams, FILE_MODEL_ROW_MAPPER));
            case DATE:
              return new ArrayList<>(jdbcTemplate.query(selectByName + selectFilterExtensionsAndCategory + " ORDER BY fileDate " + orderCriterias.getValue(), sqlParams, FILE_MODEL_ROW_MAPPER));
            case NONE:
                Object[] sqlParamsNone = new Object[params.size() + 1];
                sqlParamsNone[0] = courseSelectionParam;
                for (int i = 0; i < params.size(); i++) {
                    sqlParamsNone[i + 1] = params.get(i);
                }
                return new ArrayList<>(jdbcTemplate.query(selectAll + selectFilterExtensionsAndCategory, sqlParamsNone, FILE_MODEL_ROW_MAPPER));
            default:
                break;

        }
        return new ArrayList<>();
    }


    @Override
    public List<FileModel> listByCriteria(OrderCriterias order, SortCriterias criterias, String param, List<Long> extensions, List<Long> categories, Long userId) {
        return listByCriteria(order, criterias, param, extensions, categories, userId, -1L);
    }
}
