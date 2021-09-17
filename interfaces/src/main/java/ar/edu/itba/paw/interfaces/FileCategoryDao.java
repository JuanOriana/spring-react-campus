package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileCategory;
import java.util.List;
import java.util.Optional;

public interface FileCategoryDao {
    FileCategory create(String newCategory);
    boolean update(long fileCategoryId, String newFileCategory);
    boolean delete(long fileCategoryId);
    List<FileCategory> getCategories();
    Optional<String> getCategory(Integer categoryId);
}
