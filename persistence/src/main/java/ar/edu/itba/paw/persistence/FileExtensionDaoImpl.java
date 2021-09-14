package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.FileExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FileExtensionDaoImpl implements FileExtensionDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<FileExtension> FILE_EXTENSION_ROW_MAPPER = (rs, rowNum) -> {
        return new FileExtension(rs.getLong("fileExtensionId"), rs.getString("fileExtension"));
    };

    @Autowired
    public FileExtensionDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_extensions").usingGeneratedKeyColumns("fileExtensionId");
    }

    @Override
    public FileExtension create(String fileExtension) {
        final Map<String, Object> args = new HashMap<>();
        args.put("fileExtension", fileExtension);
        final int fileExtensionId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new FileExtension(fileExtensionId, fileExtension);
    }

    @Override
    public boolean update(long fileExtensionId, String fileExtension) {
        return jdbcTemplate.update("UPDATE file_extensions " +
                "SET fileExtension = ?" +
                "WHERE fileExtensionId = ?", new Object[]{fileExtension,fileExtensionId}) == 1;
    }

    @Override
    public boolean delete(long fileExtensionId) {
        return jdbcTemplate.update("DELETE FROM file_extensions WHERE fileExtensionId = ?", new Object[]{fileExtensionId}) == 1;
    }

    @Override
    public List<FileExtension> getExtensions() {
        return new ArrayList<>(jdbcTemplate.query("SELECT fileExtensionId, fileExtension FROM file_extensions", FILE_EXTENSION_ROW_MAPPER));
    }
}
