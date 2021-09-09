package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileModel;

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
}
