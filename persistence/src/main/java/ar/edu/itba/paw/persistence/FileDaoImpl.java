package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
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

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertCategory;
    private static final RowMapper<FileModel> FILE_MODEL_ROW_MAPPER = (rs, rowNum) -> {
        return new FileModel(rs.getInt("fileId"), rs.getLong("fileSize"), rs.getString("fileName"),
                rs.getTimestamp("fileDate").toLocalDateTime(), rs.getBytes("file"),
                new FileExtension(rs.getInt("fileExtensionId"),rs.getString("fileExtension")),
                new Course.Builder()
                        .withCourseId(rs.getInt("courseId"))
                        .withYear(rs.getInt("year"))
                        .withQuarter(rs.getInt("quarter"))
                        .withBoard(rs.getString("board"))
                        .withSubject(new Subject(rs.getInt("subjectId"), rs.getString("code"),
                                rs.getString("subjectName")))
                        .build());
    };
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
    public FileModel create(FileModel file){

        String fileExtension = getExtension(file.getName());
        FileExtension fileExtensionModel;
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM file_extensions WHERE fileExtension = ?", new Object[]{fileExtension}, Integer.class);
        if (count == 0){
            fileExtension = "other";
        }
        List<FileExtension> list = jdbcTemplate.query("SELECT fileExtensionId, fileExtension FROM file_extensions WHERE fileExtension = ?", new Object[]{fileExtension},FILE_EXTENSION_ROW_MAPPER);
        fileExtensionModel = list.get(0);

        final Map<String, Object> args = new HashMap<>();
        args.put("fileSize", file.getFile().length);
        args.put("file", file.getFile());
        args.put("fileName",file.getName());
        LocalDateTime currentTimeDate = LocalDateTime.now();
        args.put("fileDate", Timestamp.valueOf(currentTimeDate));
        args.put("fileExtensionId", fileExtensionModel.getFileExtensionId());
        args.put("courseId", file.getCourse().getCourseId());
        final int fileId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new FileModel(fileId, file.getFile().length, file.getName(), currentTimeDate,file.getFile(),fileExtensionModel, file.getCourse());
    }

    @Override
    public boolean update(long fileId, FileModel file) {
        return jdbcTemplate.update("UPDATE files " +
                "SET file = ?," +
                        "fileName = ?," +
                        "fileSize = ?," +
                        "fileDate = ?," +
                        "fileExtensionId = ?," +
                        "courseId = ? " +
                        "WHERE fileId = ?", new Object[]{file.getFile(), file.getName(), file.getFile().length, Timestamp.valueOf(LocalDateTime.now()),file.getExtension().getFileExtensionId(), file.getCourse().getCourseId(), fileId}) == 1;
    }

    @Override
    public boolean delete(long fileId) {
        return jdbcTemplate.update("DELETE FROM files WHERE fileId = ?", new Object[]{fileId}) == 1;
    }

    @Override
    public List<FileModel> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects", FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<FileModel> getById(long fileId) {
        return jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileId = ?", new Object[]{fileId},FILE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<FileModel> getByName(String fileName) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileName = ?", new Object[]{fileName}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByExtension(long extensionId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileExtensionId = ?", new Object[]{extensionId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByExtension(String extension) {
        String fileExtension = extension;
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM file_extensions WHERE fileExtension = ?", new Object[]{fileExtension}, Integer.class);
        if (count == 0){
            fileExtension = "other";
        }
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileExtension = ?", new Object[]{fileExtension},FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public boolean addCategory(long fileId, long fileCategoryId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM category_file_relationship WHERE fileId = ? AND categoryId = ?", new Object[]{fileId,fileCategoryId}, Integer.class);
        if (count == 0){
            final Map<String, Object> args = new HashMap<>();
            args.put("fileId", fileId);
            args.put("categoryId", fileCategoryId);
            jdbcInsertCategory.execute(args);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCategory(long fileId, long fileCategoryId) {
        return jdbcTemplate.update("DELETE FROM category_file_relationship WHERE fileId = ? AND categoryId = ?", new Object[]{fileId, fileCategoryId}) == 1;
    }

    @Override
    public List<FileCategory> getFileCategories(long fileId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT categoryId, categoryName FROM category_file_relationship NATURAL JOIN file_categories WHERE fileId = ?", new Object[]{fileId}, FILE_CATEGORY_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByCategory(long fileCategoryId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN category_file_relationship NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE categoryId = ?", new Object[]{fileCategoryId}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByCourseId(long courseId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension, courseId, year, quarter, board, subjectId, code, subjectName FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE courseId = ?", new Object[]{courseId}, FILE_MODEL_ROW_MAPPER));
    }

    private RowMapper<Integer> ACCESS_ROW_MAPPER = ((rs, rowNum) -> rs.getInt("userId"));

    @Override
    public boolean hasAccess(long fileId, long userId) {
        Optional<Integer> userResponse = jdbcTemplate.query("SELECT * FROM files NATURAL JOIN user_to_course WHERE " +
                "fileId = ? AND userId = ?", new Object[]{fileId, userId}, ACCESS_ROW_MAPPER).stream().findFirst();
        return userResponse.isPresent();
    }

    private String getExtension(String filename){
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        return extension;
    }
}
