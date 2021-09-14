package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.models.FileCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileCategoryServiceImpl implements FileCategoryService {

    @Autowired
    private FileCategoryDao fileCategoryDao;

    @Override
    public FileCategory create(String newCategory) {
        return fileCategoryDao.create(newCategory);
    }

    @Override
    public boolean update(long fileCategoryId, String newFileCategory) {
        return fileCategoryDao.update(fileCategoryId, newFileCategory);
    }

    @Override
    public boolean delete(long fileCategoryId) {
        return fileCategoryDao.delete(fileCategoryId);
    }

    @Override
    public List<FileCategory> getCategories() {
        return fileCategoryDao.getCategories();
    }
}
