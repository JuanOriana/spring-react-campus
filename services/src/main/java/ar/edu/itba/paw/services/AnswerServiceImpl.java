package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    @Transactional
    @Override
    public Answer create(Long examId, Long studentId, byte[] answerFile, Long answerFileSize, LocalDateTime deliveredTime) {
        Exam exam = examDao.findById(examId).orElseThrow(ExamNotFoundException::new);
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

    @Transactional
    @Override
    public boolean update(Long answerId, Answer answer) {
        return answersDao.update(answerId, answer);
    }

    @Transactional
    @Override
    public boolean delete(Long answerId) {
        return answersDao.delete(answerId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Answer> findById(Long answerId) {
        return answersDao.findById(answerId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getTotalResolvedByExam(Long examId) {
        return answersDao.getTotalResolvedByExam(examId);
    }

    @Transactional
    @Override
    public void correctExam(Long answerId, User teacher, Float score) {
        answersDao.correctExam(answerId, teacher, score);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Answer> getCorrectedAnswers(Long examId) {
        return answersDao.getCorrectedAnswers(examId);
    }


    @Transactional(readOnly = true)
    @Override
    public List<Answer> getNotCorrectedAnswers(Long examId) {
        return answersDao.getNotCorrectedAnswers(examId);
    }

    @Transactional
    @Override
    public void undoExamCorrection(Long answerId) {
        answersDao.undoExamCorrection(answerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Exam> getResolvedExams(Long studentId,Long courseId) {
        return answersDao.getResolvedExams(studentId,courseId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Exam> getUnresolvedExams(Long studentId,Long courseId) {
        return answersDao.getUnresolvedExams(studentId,courseId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getTotalAnswers(Long examId) {
        return answersDao.getTotalAnswers(examId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getTotalCorrectedAnswers(Long examId) {
        return answersDao.getTotalCorrectedAnswers(examId);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Exam, Pair<Long, Long>> getExamsAndTotals(Long courseId) {
        return answersDao.getExamsAndTotals(courseId);
    }
}
