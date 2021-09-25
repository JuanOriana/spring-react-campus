package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileDao {
    FileModel create(Long size, LocalDateTime date, String name, byte[] file, Course course);

    boolean update(Long fileId, FileModel file);

    boolean delete(Long fileId);

    List<FileModel> list(Long userId);

    Optional<FileModel> getById(Long fileId);

    List<FileModel> getByName(String fileName);

    List<FileModel> getByExtension(Long extensionId);

    List<FileModel> getByExtension(String extension);

    boolean addCategory(Long fileId, Long fileCategoryId);

    boolean removeCategory(Long fileId, Long fileCategoryId);

    List<FileCategory> getFileCategories(Long fileId);

    List<FileModel> getByCategory(Long fileCategoryId);

    List<FileModel> getByCourseId(Long courseId);

    boolean hasAccess(Long fileId, Long userId);

    // Course id must be negative when looking for all the courses.
    List<FileModel> listByCriteria(OrderCriterias order, SortCriterias criterias, String param, List<Long> extensions, List<Long> categories, Long userId, Long courseId);

    List<FileModel> listByCriteria(OrderCriterias order, SortCriterias criterias, String param, List<Long> extensions, List<Long> categories, Long userId);


}
