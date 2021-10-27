package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface AnswerDao {

    Answer create(Exam exam, User student, User teacher, FileModel answerFile,Float score,String corrections, Time deliverdTime);

    boolean update(Long answerId, Answer answer);

    boolean delete(Long answerId);

    Optional<Answer> findById(Long answerId);

    Integer getTotalResolvedByExam(Long examId);

    void correctExam(Long answerId,User teacher,Float score);

    void uncorrectExam(Long answerId);

    List<Exam> getCorrectedExams(Long courseId);

    List<Exam> getNotCorrectedExams(Long courseId);



}
