package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;

import java.sql.Time;
import java.util.Optional;

public interface AnswersDao {

    Answer create(Exam exam, User student, User teacher, FileModel answerFile,Float score,String corrections, Time deliverdTime);

    boolean update(Long answerId, Answer answer);

    boolean delete(Long answerId);

    Optional<Answer> findById(Long answerId);

}
