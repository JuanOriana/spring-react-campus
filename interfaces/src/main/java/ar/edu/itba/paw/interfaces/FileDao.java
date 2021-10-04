package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileDao {
    FileModel create(Long size, LocalDateTime date, String name, byte[] file, Course course);

    boolean update(Long fileId, FileModel file);

    boolean delete(Long fileId);

    List<FileModel> list(Long userId);

    Optional<FileModel> getById(Long fileId);

    boolean associateCategory(Long fileId, Long fileCategoryId);

    List<FileCategory> getFileCategories(Long fileId);

    List<FileModel> getByCategory(Long fileCategoryId);

    List<FileModel> getByCourseId(Long courseId);

    boolean hasAccess(Long fileId, Long userId);

    CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions, List<Long> categories,
                                       Long userId, Long courseId, CampusPageRequest pageRequest,
                                       CampusPageSort sort) throws PaginationArgumentException;

    CampusPage<FileModel> listByUser(String keyword, List<Long> extensions, List<Long> categories,
                                     Long userId, CampusPageRequest pageRequest,
                                     CampusPageSort sort) throws PaginationArgumentException;

    void incrementDownloads(Long fileId);

}
