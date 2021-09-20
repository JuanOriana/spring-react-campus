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
    FileExtensionDao fileExtensionDao;

    @Autowired
    FileCategoryDao fileCategoryDao;

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertCategory;
    private static final RowMapper<FileModel> FILE_MODEL_ROW_MAPPER = (rs, rowNum) ->

        new FileModel.Builder()
            .withFileId(rs.getLong("fileId"))
            .withSize(rs.getLong("fileSize"))
            .withName(rs.getString("fileName"))
            .withDate(rs.getTimestamp("fileDate").toLocalDateTime())
            .withFile(rs.getBytes("file"))
            .withExtension(new FileExtension(rs.getLong("fileExtensionId"),rs.getString("fileExtension")))
            .withCourse(new Course.Builder()
                    .withCourseId(rs.getLong("courseId"))
                    .withYear(rs.getInt("year"))
                    .withQuarter(rs.getInt("quarter"))
                    .withBoard(rs.getString("board"))
                    .withSubject(new Subject(rs.getLong("subjectId"), rs.getString("code"),
                            rs.getString("subjectName")))
                    .build())
            .build();

    private static final RowMapper<FileExtension> FILE_EXTENSION_ROW_MAPPER = (rs, rowNum) -> {
        return new FileExtension(rs.getLong("fileExtensionId"), rs.getString("fileExtension"));
    };
    private static final RowMapper<FileCategory> FILE_CATEGORY_ROW_MAPPER = (rs, rowNum) -> {
        return new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName"));
    };

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
                "courseId = ? " +
                "WHERE fileId = ?", new Object[]{file.getFile(), file.getName(), file.getFile().length, Timestamp.valueOf(LocalDateTime.now()), file.getExtension().getFileExtensionId(), file.getCourse().getCourseId(), fileId}) == 1;
    }

    @Override
    public boolean delete(Long fileId) {
        return jdbcTemplate.update("DELETE FROM files WHERE fileId = ?", new Object[]{fileId}) == 1;
    }

    @Override
    public List<FileModel> list(Long userId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, " +
                "quarter, board, subjectId, code, subjectName " +
                "FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN user_to_course " +
                "WHERE userId = ?", new Object[]{userId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<FileModel> getById(Long fileId) {
        return jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileId = ?", new Object[]{fileId}, FILE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<FileModel> getByName(String fileName) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileName = ?", new Object[]{fileName}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByExtension(Long extensionId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileExtensionId = ?", new Object[]{extensionId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByExtension(String extension) {
        String fileExtension = extension;
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM file_extensions WHERE fileExtension = ?", new Object[]{fileExtension}, Integer.class);
        if (count == 0) {
            fileExtension = "other";
        }
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileExtension = ?", new Object[]{fileExtension}, FILE_MODEL_ROW_MAPPER));
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
        return jdbcTemplate.update("DELETE FROM category_file_relationship WHERE fileId = ? AND categoryId = ?", new Object[]{fileId, fileCategoryId}) == 1;
    }

    @Override
    public List<FileCategory> getFileCategories(Long fileId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT categoryId, categoryName FROM category_file_relationship NATURAL JOIN file_categories WHERE fileId = ?", new Object[]{fileId}, FILE_CATEGORY_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByCategory(Long fileCategoryId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN category_file_relationship NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE categoryId = ?", new Object[]{fileCategoryId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByCourseId(Long courseId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE courseId = ?", new Object[]{courseId}, FILE_MODEL_ROW_MAPPER));
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

    public List<FileModel> listByCriteria(OrderCriterias orderCriterias, SearchingCriterias criterias, String param, List<Long> extensions, List<Long> categories) {
        StringBuilder extensionAndCategoryQuery = new StringBuilder();
        // Armado del string para filtrar por extensiones (si hay )
        if (!extensions.isEmpty()) {
            extensionAndCategoryQuery.append(" fileExtension IN (");
            for (Long extension : extensions) {
                Optional<String> exten = fileExtensionDao.getExtension(extension);
                extensionAndCategoryQuery.append("'" + (exten.orElse("other")) + "',");
            }
            extensionAndCategoryQuery.delete(extensionAndCategoryQuery.length() - 1, extensionAndCategoryQuery.length()); // todo ver si se puede mejorar esta forma "hardcodeada" de borrar la coma
            extensionAndCategoryQuery.append(")");
        }
        // Armado del string para filtrar por categorias (si hay )
        if (!categories.isEmpty()) {
            if (!extensions.isEmpty()) {
                extensionAndCategoryQuery.append(" AND");
            }
            extensionAndCategoryQuery.append(" categoryname IN ( ");
            for (Long category : categories) {
                Optional<String> cat = fileCategoryDao.getCategory(category);
                extensionAndCategoryQuery.append("'" + cat.orElse("none") + "',");
            }
            extensionAndCategoryQuery.delete(extensionAndCategoryQuery.length() - 1, extensionAndCategoryQuery.length()); // todo ver si se puede mejorar esta forma "hardcodeada" de borrar la coma
            extensionAndCategoryQuery.append(" )");
        }

        // Armado del string para buscar por nombre o fecha
        String filterStringNameAndDate = (extensions.isEmpty() && categories.isEmpty()) ? extensionAndCategoryQuery.toString() : "AND " + extensionAndCategoryQuery.toString();
        String filterStringNone = (extensions.isEmpty() && categories.isEmpty()) ? extensionAndCategoryQuery.toString() : "WHERE " + extensionAndCategoryQuery.toString();
        switch (criterias) {
            case NAME:
                    return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories WHERE fileName LIKE ?" + filterStringNameAndDate + " ORDER BY fileName "+orderCriterias.getValue(), new Object[]{("%" + param + "%")}, FILE_MODEL_ROW_MAPPER));
            case DATE:
                /// param  to DATE must be YYYY-MM-DD
               if(param.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                   String year = param.substring(0, 4);
                   String month = param.substring(5, 7);
                   String day = param.substring(8, 10);
                   return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories WHERE date_part('year', fileDate) = CAST(? AS INTEGER) AND date_part('month', fileDate) = CAST(? AS INTEGER) AND date_part('day', fileDate) = CAST(? AS INTEGER) " + filterStringNameAndDate + " ORDER BY fileDate " + orderCriterias.getValue(), new Object[]{year, month, day}, FILE_MODEL_ROW_MAPPER));
               }else if(param.isEmpty()){
                   filterStringNameAndDate = (extensions.isEmpty() && categories.isEmpty()) ? extensionAndCategoryQuery.toString() : "WHERE " + extensionAndCategoryQuery.toString();
                   return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories " + filterStringNameAndDate + " ORDER BY fileDate " + orderCriterias.getValue(), FILE_MODEL_ROW_MAPPER));
               }else{
                   return new ArrayList<>();
               }
            case NONE:
                return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN category_file_relationship NATURAL JOIN file_categories " + filterStringNone, FILE_MODEL_ROW_MAPPER));
            default:
                break;

        }
        return new ArrayList<>();
    }
}
