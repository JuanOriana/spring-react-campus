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
    List<FileModel> getByExtension(Long extensionId);

    /**
     * Attempts to get a file given its extension
     *
     * @param extension of the file/s to be retrieved
     * @return the file/s corresponding to the given extension if it exists, null otherwise
     */
    List<FileModel> getByExtension(String extension);

    /**
     * Attempts to get remove a category of a file
     *
     * @param fileId         of the file/s to remove a category from
     * @param fileCategoryId of the category to remove
     * @return true if the category was successfully removed, false otherwise
     */
    boolean removeCategory(Long fileId, Long fileCategoryId);

    /**
     * Attempts to get the categories of a file
     *
     * @param fileId of the file
     * @return the category/ies corresponding to the given file if it contains, null otherwise
     */
    List<FileCategory> getFileCategories(Long fileId);

    /**
     * Attempts to get file/s given a category
     *
     * @param fileCategoryId of the category
     * @return the file/s corresponding to the given category if they contain it, null otherwise
     */
    List<FileModel> getByCategory(Long fileCategoryId);

    /**
     * Attempts to get file/s given a list of categories
     *
     * @param categories that the file must contain
     * @return the file/s corresponding to the given categories if they contain it, null otherwise
     */
    List<FileModel> getByMultipleCategories(List<FileCategory> categories);

    /**
     * Attempts to get file/s given a course
     *
     * @param courseId that the file must beLong to
     * @return the file/s corresponding to the given course if they beLong to it, null otherwise
     */
    List<FileModel> getByCourseId(Long courseId);

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
                                     Long userId, CampusPageRequest pageRequest,
                                     CampusPageSort sort);

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
                                       Long userId, Long courseId, CampusPageRequest pageRequest,
                                       CampusPageSort sort);


    /**
     * Attempts to increment downloads counter of file
     *
     * @param fileId the file's id that has been downloaded
     */
    void incrementDownloads(Long fileId);


}
