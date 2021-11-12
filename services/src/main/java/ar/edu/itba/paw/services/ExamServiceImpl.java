package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
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

    @Autowired
    private AnswerDao answerDao;

    @Transactional
    @Override
    public Exam create(Long courseId, String title, String description, String fileName, byte[] examFile, Long examFileSize, LocalDateTime startTime, LocalDateTime endTime) {
        final Optional<Course> course = courseDao.findById(courseId);

        if (course.isPresent()) {
            FileModel fileModel = fileDao.create(examFileSize, LocalDateTime.now(), fileName, examFile, course.get(), true);
            long examCategoryId = 0;
            for (FileCategory fc : fileCategoryDao.getCategories()) {
                if (fc.getCategoryName().equalsIgnoreCase("exam")) {
                    examCategoryId = fc.getCategoryId();
                    break;
                }
            }
        fileDao.associateCategory(fileModel.getFileId(), examCategoryId);
        Exam exam = examDao.create(courseId,title, description, fileModel, startTime, endTime);
        List<User> students = courseDao.getStudents(courseId);
        answerDao.createEmptyAnswers(exam, students);
        return exam;
        } else {
            throw new CourseNotFoundException();
        }
    }

    @Transactional
    @Override
    public Exam create(Long courseId, String title, String description, FileModel examFile, LocalDateTime startTime, LocalDateTime endTime) {
        final Optional<Course> course = courseDao.findById(courseId);
        if(course.isPresent()){
            return examDao.create(courseId,title, description, examFile, startTime, endTime);
        }else{
            throw new CourseNotFoundException();
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

    @Transactional(readOnly = true)
    @Override
    public boolean belongs(Long examId, Long courseId) {
        Optional<Exam> exam = findById(examId);
        return exam.map(value -> value.getCourse().getCourseId().equals(courseId)).orElse(false);
    }
}
