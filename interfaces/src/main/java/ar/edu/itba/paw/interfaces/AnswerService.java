package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnswerService {
    /**
     * Attemps to persiste an answer entry in the data base
     *
     * @param examId       id of the exam that was resolved
     * @param studentId    id of the student that resolves the exam
     * @param answerFile   the answer given in a file
     * @param answerFileSize the size of the file
     * @param deliveredTime the last sending
     * @return an Answer instance holding the passed values
     */
    Answer create(Long examId, Long studentId, byte[] answerFile, Long answerFileSize, LocalDateTime deliveredTime);

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

    /**
     * Attempts to return the number of answers for a exam
     *
     * @param examId of the queried exam
     * @return a number that represents the total of answers
     */
    Long getTotalResolvedByExam(Long examId);

    /**
     * Attemps to persiste in the database a correction for an exam
     *
     * @param answerId of the answer that is being corrected
     * @param teacher  that corrects that answer
     * @param score    the score given
     */

    void correctExam(Long answerId, User teacher, Float score);

    /**
     * Attemps to retrieved all the corrected exams for a course
     *
     * @param courseId of the queried course
     * @return a list of all the exams that are already corrected
     */
    List<Answer> getCorrectedAnswers(Long courseId);

    /**
     * Attemps to retrieved all the not corrected exams for a course
     *
     * @param courseId of the queried course
     * @return a list of all the exams that are not already corrected
     */
    List<Answer> getNotCorrectedAnswers(Long courseId);

    /**
     * Attemps to delete a correction to an answer
     *
     * @param answerId of the answer to be deleted the correction
     */
    void undoExamCorrection(Long answerId);

    /**
     * Attempts to return a list of the resolved exams by the user
     *
     * @param studentId of the queried user
     * @return a list of all the exams resolved by the user
     */
    List<Exam> getResolvedExams(Long studentId,Long courseId);

    /**
     * Attempts to return a list of the unresolved exams by the user
     *
     * @param studentId of the queried user
     * @return a list of all the exams unresolved by the user
     */
    List<Exam> getUnresolvedExams(Long studentId,Long courseId);

    /**
     * Attempts to return the number of answers to an exam
     *
     * @param examId of the queried exam
     * @return the number of answers for that exam
     */
    Long getTotalAnswers(Long examId);

    /**
     * Attempts to return the number of corrected answers to an exam
     *
     * @param examId of the queried exam
     * @return the number of corrected answers to an exam
     */
    Long getTotalCorrectedAnswers(Long examId);
}
