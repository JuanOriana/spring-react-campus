package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface FileService {

    /**
     * Attempts to persist a file entry in the database
     *
     * @param file The file to be persisted in the database
     * @return the file if it was successfully added
     */
    FileModel create(FileModel file);

    /**
     * Attempts to update a file
     *
     * @param fileId of the file to be modified
     * @param file modified file
     * @return true if the file was successfully updated, false otherwise
     */
    boolean update(long fileId, FileModel file);

    /**
     * Attempts to delete a file
     *
     * @param fileId of the file to be deleted
     * @return true if the file was successfully removed, false otherwise
     */
    boolean delete(long fileId);

    /**
     * Gets all the current available files
     * @return list containing all the current available files (if any)
     */
    List<FileModel> list();

    /**
     * Attempts to get a file given an id
     *
     * @param fileId of the file to be retrieved
     * @return the file corresponding to the given id if it exists, null otherwise
     */
    Optional<FileModel> getById(long fileId);

    /**
     * Attempts to get a file given a name
     *
     * @param fileName of the file/s to be retrieved
     * @return the file/s corresponding to the given name if it exists, null otherwise
     */
    List<FileModel> getByName(String fileName);

    /**
     * Attempts to get a file given its extension
     *
     * @param extensionId of the file/s to be retrieved
     * @return the file/s corresponding to the given extension if it exists, null otherwise
     */
    List<FileModel> getByExtension(long extensionId);

    /**
     * Attempts to get add a category to a file
     *
     * @param fileId of the file/s to add a category
     * @param fileCategoryId of the category to add
     * @return true if the category was successfully added, false otherwise
     */
    boolean addCategory(long fileId, long fileCategoryId);

    /**
     * Attempts to get remove a category of a file
     *
     * @param fileId of the file/s to remove a category from
     * @param fileCategoryId of the category to remove
     * @return true if the category was successfully removed, false otherwise
     */
    boolean removeCategory(long fileId, long fileCategoryId);

    /**
     * Attempts to get the categories of a file
     *
     * @param fileId of the file
     * @return the category/ies corresponding to the given file if it contains, null otherwise
     */
    List<FileCategory> getFileCategories(long fileId);

    /**
     * Attempts to get file/s given a category
     *
     * @param fileCategoryId of the category
     * @return the file/s corresponding to the given category if they contain it, null otherwise
     */
    List<FileModel> getByCategory(long fileCategoryId);

    /**
     * Attempts to get file/s given a list of categories
     *
     * @param categories that the file must contain
     * @return the file/s corresponding to the given categories if they contain it, null otherwise
     */
    List<FileModel> getByMultipleCategories(List<FileCategory> categories);
}
