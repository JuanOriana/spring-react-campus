package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnswerDao {

    Answer create(Exam exam, Long studentId, Long teacherId, FileModel answerFile, Float score, String corrections, LocalDateTime deliveredTime);

    boolean update(Long answerId, Answer answer);

    Answer updateEmptyAnswer(Long examId, User student,Long teacherId, Answer answer);
    boolean delete(Long answerId);

    Optional<Answer> findById(Long answerId);

    Long getTotalResolvedByExam(Long examId);

    void correctExam(Long answerId, User teacher, Float score);

    void undoExamCorrection(Long answerId);

    List<Answer> getCorrectedAnswers(Long examId);

    List<Answer> getNotCorrectedAnswers(Long examId);

    List<Exam> getResolvedExams(Long studentId,Long courseId);

    List<Exam> getUnresolvedExams(Long studentId,Long courseId);

    Long getTotalAnswers(Long examId);

    Long getTotalCorrectedAnswers(Long examId);

    Map<Exam, Pair<Long,Long>> getExamsAndTotals(Long courseId);

    void createEmptyAnswers(Exam exam,List<User> students);
}
