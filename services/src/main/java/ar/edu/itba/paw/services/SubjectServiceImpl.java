package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectDao subjectDao;

    @Override
    public Subject create(String code, String name) {
        return subjectDao.create(code, name);
    }

    @Override
    public boolean update(Long subjectId, String code, String name) {
        return subjectDao.update(subjectId, code, name);
    }

    @Override
    public boolean delete(Long subjectId) {
        return subjectDao.delete(subjectId);
    }

    @Override
    public List<Subject> list() {
        return subjectDao.list();
    }
}
