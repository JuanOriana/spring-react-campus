package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExamService {


    /**
     *  Attempts to create an exam
     * @param courseId
     * @param title
     * @param description
     * @param fileName
     * @param examFile
     * @param examFileSize
     * @param startTime
     * @param endTime
     * @return
     */
    Exam create(Long courseId, String title, String description, String fileName, byte[] examFile, Long examFileSize, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * @param courseId
     * @param title
     * @param description
     * @param examFile
     * @param startTime
     * @param endTime
     * @return
     */
    Exam create(Long courseId, String title, String description, FileModel examFile, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Attempts to update an exam
     *
     * @param examId of the exam to be modified
     * @param exam   modified exam
     * @return true if the exam was successfully updated, false otherwise
     */
    boolean update(Long examId, Exam exam);

    /**
     * Attempts to delete an exam
     *
     * @param examId of the exam to be deleted
     * @return true if the exam was successfully removed, false otherwise
     */
    boolean delete(Long examId);

    /**
     * Gets all the current available exams of a course
     * @param courseId id of the course
     * @return list containing all the current available exams (if any)
     */
    List<Exam> listByCourse(Long courseId);

    /**
     * Attempts to get an exam given an id
     * @param examId of the exam to be retrieved
     * @return the exam corresponding to the given id if it exists, null otherwise
     */
    Optional<Exam> findById(Long examId);

    /**
     * Determines if an exam belongs to a course
     * @param examId of the queried exam
     * @param courseId of the queried course
     * @return true if the exam belongs to the course, false otherwise
     */
    boolean belongs(Long examId, Long courseId);

    /**
     * Attempts to return a map of exams with the number of answers and corrected answers
     *
     * @param courseId of the queried course
     * @return a map with an Exam as a key and a Pair<Long,Long> where the first is totalAnswers and the second is totalCorrectedAnswers
     */
    Map<Exam, Pair<Long, Long>> getExamsAndTotals(Long courseId);

    /**
     * Attemps to return a map of exams with de average of score for each exam in the course
     *
     * @param courseId of the queried course
     * @return a map with a exam as key and double as value that is the average score for that exam
     */
    Map<Exam, Double> getExamsAverage(Long courseId);

    /**
     * Attempts to return a list of the resolved exams by the user
     *
     * @param studentId of the queried user
     * @return a list of all the exams resolved by the user
     */
    List<Exam> getResolvedExams(Long studentId, Long courseId);

    /**
     * Attempts to return a list of the unresolved exams by the user
     *
     * @param studentId of the queried user
     * @return a list of all the exams unresolved by the user
     */
    List<Exam> getUnresolvedExams(Long studentId, Long courseId);

    /**
     * Attempts to return the number of answers for a exam
     *
     * @param examId of the queried exam
     * @return a number that represents the total of answers
     */
    Long getTotalResolvedByExam(Long examId);

    /**
     * Attemps to get the average of score for an exam
     *
     * @param examId of the queried exam
     * @return the average of scores for the exam
     */
    Double getAverageScoreOfExam(Long examId);

}
