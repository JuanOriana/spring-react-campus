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
import java.util.List;
import java.util.Optional;

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
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files");
    }

    @Override
    public FileModel create(FileModel file) {
        return null;
    }

    @Override
    public boolean update(long fileId, FileModel file) {
        return false;
    }

    @Override
    public boolean delete(long fileId) {
        return false;
    }

    @Override
    public List<FileModel> list() {
        return null;
    }

    @Override
    public Optional<FileModel> getById(long fileId) {
        return Optional.empty();
    }
}
