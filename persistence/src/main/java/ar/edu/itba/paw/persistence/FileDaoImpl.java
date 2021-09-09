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
        return new FileModel(rs.getInt("fileId"), rs.getLong("size"), new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName")), rs.getString("name"), rs.getDate("date"), rs.getObject("file", File.class));
    };

    @Autowired
    public FileDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files").usingGeneratedKeyColumns("fileId");
    }

    @Override
    public FileModel create(FileModel file) throws FileNotFoundException {
        final Map<String, Object> args = new HashMap<>();
        args.put("size", file.getFile().length());
        args.put("file", new FileReader(file.getFile()));
        args.put("name",file.getFile().getName());
        LocalDate currentTime = java.time.LocalDate.now();
        args.put("date", currentTime);
        args.put("categoryId", file.getCategory().getCategoryId());
        final int fileId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new FileModel(fileId, file.getFile().length(), file.getCategory(), file.getFile().getName(), Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant()), file.getFile());
    }

    @Override
    public boolean update(long fileId, FileModel file) throws FileNotFoundException {
        return jdbcTemplate.update("UPDATE files " +
                "SET file = ?," +
                        "name = ?," +
                        "size = ?," +
                        "date = ?," +
                        "categoryId = ?," +
                        "WHERE fileId = ?", new Object[]{new FileReader(file.getFile()), file.getFile().getName(), file.getFile().length(), java.time.LocalDate.now(), file.getCategory().getCategoryId(), fileId}) == 1;
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
        return Optional.empty();
    }
}
