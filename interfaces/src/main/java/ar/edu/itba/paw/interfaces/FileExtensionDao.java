package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.FileExtension;

import java.util.List;

public interface FileExtensionDao{
    FileExtension create(String fileExtension);
    boolean update(long fileExtensionId, String fileExtension);
    boolean delete(long fileExtensionId);
    List<FileExtension> getExtensions();
}
