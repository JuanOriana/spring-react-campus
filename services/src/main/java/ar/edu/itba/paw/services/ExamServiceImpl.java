package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
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
    private CourseDao courseDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileCategoryDao fileCategoryDao;

    @Override
    public Exam create(Long courseId, String title, String description, byte[] examFile, Long examFileSize, LocalDateTime startTime, LocalDateTime endTime) {
        final Optional<Course> course = courseDao.findById(courseId);

        if (course.isPresent()) {
            FileModel fileModel = fileDao.create(examFileSize, LocalDateTime.now(), title, examFile, course.get(), true);
            long examCategoryId = 0;
            for (FileCategory fc : fileCategoryDao.getCategories()) {
                if (fc.getCategoryName().equalsIgnoreCase("exam")) {
                    examCategoryId = fc.getCategoryId();
                    break;
                }
            }
            fileDao.associateCategory(fileModel.getFileId(), examCategoryId);
        return examDao.create(courseId,title, description, fileModel,null, startTime, endTime);
        } else {
            throw new CourseNotFoundException();
            //TODO:log error
        }
    }

    @Override
    public Exam create(Long courseId, String title, String description, FileModel examFile, LocalDateTime startTime, LocalDateTime endTime) {
        final Optional<Course> course = courseDao.findById(courseId);
        if(course.isPresent()){
            return examDao.create(courseId,title, description, examFile,null, startTime, endTime);
        }else{
            throw new CourseNotFoundException();
            //TODO:log error
        }
    }

    @Transactional
    @Override
    public boolean update(Long examId, Exam exam) {
        return examDao.update(examId, exam);
    }

    @Transactional
    @Override
    public boolean delete(Long examId) {
        return examDao.delete(examId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Exam> listByCourse(Long courseId) {
        return examDao.listByCourse(courseId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Exam> findById(Long examId) {
        return examDao.findById(examId);
    }
}
