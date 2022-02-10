package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnswerService {
    /**
     * Attempts to update an answer
     *
     * @param answerId of the answer to be modified
     * @param answer   modified answer
     * @return true if the answer was successfully update, false otherwise
     */
    boolean update(Long answerId, Answer answer);

    /**
     * Attempts to update an answer
     *
     * @param examId         of the exam's answer to be modified
     * @param student        "owner" of the answer
     * @param answerFileName the answer file name
     * @param answerFile     the answer file
     * @param answerFileSize the answer file size
     * @param deliveredTime  the answer delivered time
     * @return updated answer object
     */
    Answer updateEmptyAnswer(Long examId, User student, String answerFileName, byte[] answerFile, Long answerFileSize, LocalDateTime deliveredTime);

    /**
     * Attempts to delete an answer
     *
     * @param answerId of the answer to be deleted
     * @return true if the answer was successfully removed, false otherwise
     */

    boolean delete(Long answerId);

    /**
     * Attempts to get an answer given an id
     *
     * @param answerId of the answer to be retrieved
     * @return an option with the answer corresponding to the given id if exists, else an empty optional
     */

    Optional<Answer> findById(Long answerId);

    /**
     * Attempts to persist in the database a correction for an exam
     *
     * @param answerId    of the answer that is being corrected
     * @param teacher     that corrects that answer
     * @param score       the score given
     * @param corrections the corrections of the sent exam
     */

    void correctExam(Long answerId, User teacher, Float score, String corrections);

    /**
     * Attempts to delete a correction to an answer
     *
     * @param answerId of the answer to be deleted the correction
     */
    void undoExamCorrection(Long answerId);


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

    /**
     * Determines if user delivered an answer or not for an exam
     *
     * @param examId of the queried exam
     * @param userId of the queried user
     * @return true if the user delivered an answer, false otherwise
     */
    boolean didUserDeliver(Long examId, Long userId);

    Answer findUserAnswer(Long examId, Long userId, Long courseId);

    /**
     * Returns a filtered-paginated list of answers
     *
     * @param examId   of the queried exam
     * @param filter   applied to the answer query
     * @param page     to be queried
     * @param pageSize of the queried page
     * @return page containing a list of answers
     */
    CampusPage<Answer> getFilteredAnswers(Long examId, String filter, Integer page, Integer pageSize);

    /**
     * Returns a list of answers for a specific user
     *
     * @param userId   of the user to query the marks from
     * @param courseId of the course
     * @return map of exam, answer entry for a specific user
     */
    List<Answer> getMarks(Long userId, Long courseId);


    /**
     * Attempts to return the average score of a student in a course.
     *
     * @param studentId of the queried student
     * @param courseId  of the queried course
     * @return the average score fot the student in the course
     */
    Double getAverageOfUserInCourse(Long studentId, Long courseId);
}
