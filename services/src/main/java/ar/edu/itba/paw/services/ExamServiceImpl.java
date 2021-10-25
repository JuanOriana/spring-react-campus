package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ExamDao;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.ExamModel;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileCategoryDao fileCategoryDao;

    @Override
    public ExamModel create(String title, String instructions, Long size, String name, byte[] file, Course course, LocalDateTime startDate, LocalDateTime finishDate) {
        FileModel fileModel = fileDao.create(size, LocalDateTime.now(), name, file, course);
        long examCategoryId = 0;
        for (FileCategory fc : fileCategoryDao.getCategories()){
            if (fc.getCategoryName().equals("Exam") || fc.getCategoryName().equals("exam")){
                examCategoryId = fc.getCategoryId();
                break;
            }
        }
        fileDao.associateCategory(fileModel.getFileId(), examCategoryId);
        return examDao.create(title, instructions, fileModel, startDate, finishDate);
    }

    @Transactional
    @Override
    public ExamModel create(String title, String instructions, FileModel file, LocalDateTime startDate, LocalDateTime finishDate) {
        return examDao.create(title, instructions, file, startDate, finishDate);
    }

    @Transactional
    @Override
    public boolean update(Long examId, ExamModel exam) {
        return examDao.update(examId, exam);
    }

    @Transactional
    @Override
    public boolean delete(Long examId) {
        return examDao.delete(examId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamModel> list(Long courseId) {
        return examDao.list(courseId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ExamModel> findById(Long examId) {
        return examDao.findById(examId);
    }
}
