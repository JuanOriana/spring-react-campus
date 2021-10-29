package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answersDao;

    @Autowired
    private ExamDao examDao;

    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileCategoryDao fileCategoryDao;


    @Override
    public Answer create(Long examId, Long studentId, byte[] answerFile, Long answerFileSize, LocalDateTime deliveredTime) {
        Exam exam = examDao.findById(examId).orElseThrow(RuntimeException::new); // TODO: Lanzar una excepcion propia
        FileModel answerFileModel = fileDao.create(answerFileSize, deliveredTime, exam.getTitle()+" answer from "+studentId, answerFile, exam.getCourse());
        long examCategoryId = 0;
        for (FileCategory fc : fileCategoryDao.getCategories()) {
            if (fc.getCategoryName().equalsIgnoreCase("exam")) {
                examCategoryId = fc.getCategoryId();
                break;
            }
        }
        fileDao.associateCategory(answerFileModel.getFileId(), examCategoryId);
        return answersDao.create(exam, studentId, null, answerFileModel, null, null, deliveredTime);
    }

    @Override
    public boolean update(Long answerId, Answer answer) {
        return answersDao.update(answerId, answer);
    }

    @Override
    public boolean delete(Long answerId) {
        return answersDao.delete(answerId);
    }

    @Override
    public Optional<Answer> findById(Long answerId) {
        return answersDao.findById(answerId);
    }

    @Override
    public Integer getTotalResolvedByExam(Long examId) {
        return answersDao.getTotalResolvedByExam(examId);
    }

    @Override
    public void correctExam(Long answerId, User teacher, Float score) {
        answersDao.correctExam(answerId, teacher, score);
    }

    @Override
    public List<Answer> getCorrectedAnswers(Long courseId) {
        return answersDao.getCorrectedAnswers(courseId);
    }


    @Override
    public List<Answer> getNotCorrectedAnswers(Long courseId) {
        return answersDao.getNotCorrectedAnswers(courseId);
    }

    @Override
    public void undoExamCorrection(Long answerId) {
        answersDao.undoExamCorrection(answerId);
    }

    @Override
    public List<Exam> getResolvedExams(Long studentId) {
        return answersDao.getResolvedExams(studentId);
    }

    @Override
    public List<Exam> getUnresolvedExams(Long studentId) {
        return answersDao.getUnresolvedExams(studentId);
    }

    @Override
    public Integer getTotalAnswers(Long examId) {
        return answersDao.getTotalAnswers(examId);
    }

    @Override
    public Integer getTotalCorrectedAnswers(Long examId) {
        return answersDao.getTotalCorrectedAnswers(examId);
    }
}
