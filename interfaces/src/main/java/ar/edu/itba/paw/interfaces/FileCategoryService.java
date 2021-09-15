package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileCategory;

import java.util.List;

public interface FileCategoryService {

    /**
     * Attempts to persist a category entry in the database
     *
     * @param newCategory The category to be persisted in the database
     * @return the FileCategory Model object if it was successfully added
     */
    FileCategory create(String newCategory);

    /**
     * Attempts to update a category
     *
     * @param fileCategoryId of the category to be modified
     * @param newFileCategory modified category string
     * @return true if the category was successfully updated, false otherwise
     */
    boolean update(long fileCategoryId, String newFileCategory);

    /**
     * Attempts to delete a category
     *
     * @param fileCategoryId of the category to be deleted
     * @return true if the category was successfully removed, false otherwise
     */
    boolean delete(long fileCategoryId);

    /**
     * Gets all the current available categories
     * @return list containing all the current available categories (if any)
     */
    List<FileCategory> getCategories();
}
