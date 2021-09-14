package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileExtension;

import java.util.List;

public interface FileExtensionService {

    /**
     * Attempts to persist an extension entry in the database
     *
     * @param fileExtension The extension to be persisted in the database
     * @return the FileExtension Model object if it was successfully added
     */
    FileExtension create(String fileExtension);

    /**
     * Attempts to update an extension
     *
     * @param fileExtensionId of the extension to be modified
     * @param fileExtension modified extension
     * @return true if the extension was successfully updated, false otherwise
     */
    boolean update(long fileExtensionId, String fileExtension);

    /**
     * Attempts to delete an extension
     *
     * @param fileExtensionId of the extension to be deleted
     * @return true if the extension was successfully removed, false otherwise
     */
    boolean delete(long fileExtensionId);

    /**
     * Gets all the current available file extensions
     * @return list containing all the current available extensions (if any)
     */
    List<FileExtension> getExtensions();
}
