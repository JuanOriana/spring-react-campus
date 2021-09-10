package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Repository
public class FileDaoImpl implements FileDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<FileModel> FILE_MODEL_ROW_MAPPER = (rs, rowNum) -> {
        return new FileModel(rs.getInt("fileId"), rs.getLong("size"), new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName")), rs.getString("name"), rs.getDate("date"), rs.getObject("file", byte[].class), rs.getString("extension"));
    };

    @Autowired
    public FileDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files").usingGeneratedKeyColumns("fileId");
    }

    @Override
    public FileModel create(FileModel file) throws FileNotFoundException {
        final Map<String, Object> args = new HashMap<>();
        args.put("size", file.getFile().length);
        args.put("file", file.getFile());
        args.put("name",file.getName());
        LocalDate currentTime = java.time.LocalDate.now();
        args.put("date", currentTime);
        args.put("categoryId", file.getCategory().getCategoryId());
        String fileExtension = "";
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            fileExtension = file.getName().substring(i+1);
        }
        final int fileId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new FileModel(fileId, file.getFile().length, file.getCategory(), file.getName(), Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant()),file.getFile(),fileExtension);
    }

    @Override
    public boolean update(long fileId, FileModel file) throws FileNotFoundException {
        String fileExtension = "";
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            fileExtension = file.getName().substring(i+1);
        }
        return jdbcTemplate.update("UPDATE files " +
                "SET file = ?," +
                        "name = ?," +
                        "size = ?," +
                        "date = ?," +
                        "categoryId = ?," +
                        "extension = ?," +
                        "WHERE fileId = ?", new Object[]{file.getFile(), file.getName(), file.getFile().length, java.time.LocalDate.now(), file.getCategory().getCategoryId(),fileExtension, fileId}) == 1;
    }

    @Override
    public boolean delete(long fileId) {
        return jdbcTemplate.update("DELETE FROM files WHERE fileId = ?", new Object[]{fileId}) == 1;
    }

    @Override
    public List<FileModel> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileId, size, categoryId, categoryName, name, date, file FROM files NATURAL JOIN filecategories", FILE_MODEL_ROW_MAPPER));
    }

    @Override
    public Optional<FileModel> getById(long fileId) {
        return jdbcTemplate.query("SELECT fileId, size, categoryId, categoryName, name, date, file, extension FROM files NATURAL JOIN filecategories WHERE fileId = ?", new Object[]{fileId},FILE_MODEL_ROW_MAPPER).stream().findFirst();
    }
}
