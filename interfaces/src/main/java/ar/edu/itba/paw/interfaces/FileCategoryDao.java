package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;

import java.util.List;

public interface FileCategoryDao {
    FileCategory create(String newCategory);
    boolean update(long fileCategoryId, String newFileCategory);
    boolean delete(long fileCategoryId);
    List<FileCategory> getCategories();
}
