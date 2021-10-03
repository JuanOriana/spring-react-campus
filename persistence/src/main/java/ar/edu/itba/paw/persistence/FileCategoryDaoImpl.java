package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.models.FileCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.*;

@Repository
public class FileCategoryDaoImpl implements FileCategoryDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<FileCategory> FILE_CATEGORY_ROW_MAPPER = (rs, rowNum) ->
        new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName"));



    @Autowired
    public FileCategoryDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_categories").usingGeneratedKeyColumns("categoryId");
    }

    @Override
    public FileCategory create(String newCategory) {
        final Map<String, Object> args = new HashMap<>();
        args.put("categoryName", newCategory);
        final int categoryId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new FileCategory(categoryId, newCategory);
    }

    @Override
    public boolean update(long fileCategoryId, String newFileCategory) {
        return jdbcTemplate.update("UPDATE file_categories " +
                "SET categoryName = ?" +
                "WHERE categoryId = ?", newFileCategory,fileCategoryId) == 1;
    }

    @Override
    public boolean delete(long fileCategoryId) {
        return jdbcTemplate.update("DELETE FROM file_categories WHERE categoryId = ?", fileCategoryId) == 1;
    }

    @Override
    public List<FileCategory> getCategories() {
        return new ArrayList<>(jdbcTemplate.query("SELECT categoryId, categoryName FROM file_categories", FILE_CATEGORY_ROW_MAPPER));
    }

    @Override
    public Optional<FileCategory> getById(Long categoryId) {
        return jdbcTemplate.query("SELECT * FROM file_categories WHERE categoryId = ?",
                new Object[]{categoryId}, FILE_CATEGORY_ROW_MAPPER).stream().findFirst();
    }
}
