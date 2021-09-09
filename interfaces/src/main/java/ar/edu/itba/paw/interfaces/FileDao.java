package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileModel;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface FileDao {
    FileModel create(FileModel file) throws FileNotFoundException;
    boolean update(long fileId, FileModel file) throws FileNotFoundException;
    boolean delete(long fileId);
    List<FileModel> list();
    Optional<FileModel> getById(long fileId);
}
