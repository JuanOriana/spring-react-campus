package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AnswerDao;
import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answersDao;

    @Override
    public Answer create(Exam exam, User student, FileModel answerFile, LocalDateTime deliveredTime) {
        return answersDao.create(exam, student, null, answerFile, null, null, deliveredTime);
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
}
