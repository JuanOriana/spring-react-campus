package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import java.util.List;
import java.util.Optional;

public interface FileService {

    /**
     * Attempts to persist a file entry in the database
     *
     * @param size   of the file
     * @param name   of the file
     * @param file   representation in byte array
     * @param course where the file belongs to
     * @return the file if it was successfully added
     */
    FileModel create(Long size, String name, byte[] file, Course course);

    /**
     * Attempts to persist a file entry in the database
     *
     * @param size           of the file
     * @param name           of the file
     * @param file           representation in byte array
     * @param course         where the file belongs to
     * @param fileCategories list of categories
     * @return the file if it was successfully added
     */
    FileModel create(Long size, String name, byte[] file, Course course, List<Long> fileCategories);

    /**
     * Attempts to update a file
     *
     * @param fileId of the file to be modified
     * @param file   modified file
     * @return true if the file was successfully updated, false otherwise
     */
    boolean update(Long fileId, FileModel file);

    /**
     * Attempts to delete a file
     *
     * @param fileId of the file to be deleted
     * @return true if the file was successfully removed, false otherwise
     */
    boolean delete(Long fileId);

    /**
     * Gets all the current available files
     *
     * @return list containing all the current available files (if any)
     */
    List<FileModel> list(Long userId);

    /**
     * Attempts to get a file given an id
     *
     * @param fileId of the file to be retrieved
     * @return the file corresponding to the given id if it exists, null otherwise
     */
    Optional<FileModel> getById(Long fileId);


    /**
     * Returns if a user has access to the given file
     *
     * @param fileId of the file to query
     * @param userId of the user to check the privileges
     * @return true if the user has access, false otherwise
     */
    boolean hasAccess(Long fileId, Long userId);


    /**
     * Attempts to get file/s that the user is authorized given some criterias of searching and filter
     *
     * @param keyword      establish the key word to be search. In case of not searching nothing can be null or ""
     * @param extensions List of ids of the extensions that are expected in the return list. To get all send an empty list
     * @param categories List of ids of the categories that are expected in the return list. To get all send an empty list
     * @param userId     the ID of the user that is searching
     * @return a list containing all the files that match with all the criterias given (if any).
     */
    CampusPage<FileModel> listByUser(String keyword, List<Long> extensions, List<Long> categories,
                                     Long userId, Integer page, Integer pageSize, String direction,
                                     String property);

    /**
     * Attempts to get file/s from a course given some criterias of searching and filter
     *
     * @param keyword      establish the key word to be search. In case of not searching nothing can be null or ""
     * @param extensions List of ids of the extensions that are expected in the return list. To get all send an empty list
     * @param categories List of ids of the categories that are expected in the return list. To get all send an empty list
     * @param userId     the ID of the user that is searching
     * @param courseId   of the course
     * @return a list containing all the files that match with all the criterias given (if any).
     */
    CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions, List<Long> categories,
                                       Long userId, Long courseId, Integer page, Integer pageSize,
                                       String direction, String property);


    /**
     * Attempts to increment downloads counter of file
     *
     * @param fileId the file's id that has been downloaded
     */
    void incrementDownloads(Long fileId);


}
