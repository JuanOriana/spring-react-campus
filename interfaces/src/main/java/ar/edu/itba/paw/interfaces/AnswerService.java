package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;

import java.sql.Time;
import java.util.Optional;

public interface AnswerService {
    /**
     * Attemps to persiste an answer entry in the data base
     *
     * @param exam         that was resolved
     * @param student      that resolves the exam
     * @param answerFile   the answer given in a file
     * @param deliverdTime the last sending
     * @return an Answer instance holding the passed values
     */
    Answer create(Exam exam, User student, FileModel answerFile, Time deliverdTime);

    /**
     * Attemps to update an answer
     *
     * @param answerId of the answer to be modified
     * @param answer   modified answer
     * @return true if the answer was succefully update, false otherwise
     */
    boolean update(Long answerId, Answer answer);

    /**
     * Attemps to delete an answer
     *
     * @param answerId of the answer to be deleted
     * @return true if the answer was succefully removed, false otherwise
     */

    boolean delete(Long answerId);

    /**
     * Attemppts to get an answer given an id
     *
     * @param answerId of the answer to be retrieved
     * @return an option with the answer corresponding to the given id if exists, else an empty optional
     */

    Optional<Answer> findById(Long answerId);
}
