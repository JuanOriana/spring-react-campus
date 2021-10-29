package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnswerDao {

    Answer create(Exam exam, Long studentId, Long teacherId, FileModel answerFile, Float score, String corrections, LocalDateTime deliveredTime);

    boolean update(Long answerId, Answer answer);

    boolean delete(Long answerId);

    Optional<Answer> findById(Long answerId);

    Integer getTotalResolvedByExam(Long examId);

    void correctExam(Long answerId, User teacher, Float score);

    void undoExamCorrection(Long answerId);

    List<Answer> getCorrectedAnswers(Long courseId);

    List<Answer> getNotCorrectedAnswers(Long courseId);

    List<Exam> getResolvedExams(Long studentId,Long courseId);

    List<Exam> getUnresolvedExams(Long studentId,Long courseId);

    Integer getTotalAnswers(Long examId);

    Integer getTotalCorrectedAnswers(Long examId);
}
