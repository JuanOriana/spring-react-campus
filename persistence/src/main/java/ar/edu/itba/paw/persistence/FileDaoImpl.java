package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileExtensionModel;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Repository
public class FileDaoImpl implements FileDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertCategory;
    private static final RowMapper<FileModel> FILE_MODEL_ROW_MAPPER = (rs, rowNum) -> {
        return new FileModel(rs.getInt("fileId"), rs.getLong("fileSize"), rs.getString("fileName"), rs.getDate("fileDate"), rs.getObject("file", byte[].class), new FileExtensionModel(rs.getLong("fileExtensionId"),rs.getString("fileExtension")));
    };
    private static final RowMapper<FileCategory> FILE_CATEGORY_ROW_MAPPER = (rs, rowNum) -> {
      return new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName"));
    };

    @Autowired
    public FileDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files").usingGeneratedKeyColumns("fileId");
        jdbcInsertCategory = new SimpleJdbcInsert(jdbcTemplate).withTableName("category_file_relationship");
    }

    @Override
    public FileModel create(FileModel file){
        final Map<String, Object> args = new HashMap<>();
        args.put("fileSize", file.getFile().length);
        args.put("file", file.getFile());
        args.put("fileName",file.getName());
        LocalDate currentTime = java.time.LocalDate.now();
        Date currentTimeDate = Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        args.put("fileDate", currentTimeDate);
        args.put("fileExtensionId", file.getExtension().getFileExtensionId());
        final int fileId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new FileModel(fileId, file.getFile().length, file.getName(), currentTimeDate,file.getFile(),file.getExtension());
    }

    @Override
    public boolean update(long fileId, FileModel file) {
        return jdbcTemplate.update("UPDATE files " +
                "SET file = ?," +
                        "fileName = ?," +
                        "fileSize = ?," +
                        "fileDate = ?," +
                        "fileExtensionId = ? " +
                        "WHERE fileId = ?", new Object[]{file.getFile(), file.getName(), file.getFile().length, java.time.LocalDate.now(),file.getExtension().getFileExtensionId(), fileId}) == 1;
    }

    @Override
    public boolean delete(long fileId) {
        return jdbcTemplate.update("DELETE FROM files WHERE fileId = ?", new Object[]{fileId}) == 1;
    }

    @Override
    public List<FileModel> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension FROM files NATURAL JOIN file_extensions", FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<FileModel> getById(long fileId) {
        return jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension  FROM files NATURAL JOIN file_extensions WHERE fileId = ?", new Object[]{fileId},FILE_MODEL_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<FileModel> getByName(String fileName) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension FROM files NATURAL JOIN file_extensions WHERE fileName = ?", new Object[]{fileName}, FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public List<FileModel> getByExtension(long extensionId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension FROM files NATURAL JOIN file_extensions WHERE fileExtensionId = ?", new Object[]{extensionId}, FILE_MODEL_ROW_MAPPER));
    }

    // TODO: test in FileDaoImplTest
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

    // TODO: test in FileDaoImplTest
    @Override
    public boolean removeCategory(long fileId, long fileCategoryId) {
        return jdbcTemplate.update("DELETE FROM category_file_relationship WHERE fileId = ? AND categoryId = ?", new Object[]{fileId, fileCategoryId}) == 1;
    }

    // TODO: test in FileDaoImplTest
    @Override
    public List<FileCategory> getFileCategories(long fileId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT categoryId, categoryName FROM category_file_relationship NATURAL JOIN file_categories WHERE fileId = ?", new Object[]{fileId}, FILE_CATEGORY_ROW_MAPPER));
    }

    // TODO: test in FileDaoImplTest
    @Override
    public List<FileModel> getByCategory(long fileCategoryId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, fileSize, fileName, fileDate, file, fileExtensionId, fileExtension FROM files NATURAL JOIN category_file_relationship NATURAL JOIN file_extensions WHERE categoryId = ?", new Object[]{fileCategoryId}, FILE_MODEL_ROW_MAPPER));
    }
}
