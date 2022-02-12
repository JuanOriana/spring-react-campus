package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDao subjectDao;

    @Autowired
    public SubjectServiceImpl(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    @Transactional
    @Override
    public Subject create(String code, String name) {
        return subjectDao.create(code, name);
    }

    @Transactional
    @Override
    public boolean update(Long subjectId, String code, String name) {
        return subjectDao.update(subjectId, code, name);
    }

    @Override
    public Optional<Subject> findById(Long subjectId) {
        return subjectDao.findById(subjectId);
    }

    @Transactional
    @Override
    public boolean delete(Long subjectId) {
        return subjectDao.delete(subjectId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Subject> list() {
        return subjectDao.list();
    }
}
