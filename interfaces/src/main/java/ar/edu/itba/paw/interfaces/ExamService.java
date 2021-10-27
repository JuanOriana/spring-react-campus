package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExamService {


    /**
     *  Attemps to create an exam
     * @param courseId //TODO: FILL THIS PARAMETERS
     * @param title
     * @param description
     * @param examFile
     * @param examFileSize
     * @param answersFile
     * @param answeFileSize
     * @param startTime
     * @param endTime
     * @return
     */
    Exam create(Long courseId, String title, String description, byte[] examFile,Long examFileSize, Time startTime, Time endTime);

    /**
     *  TODO : FILL THIS PARAMETERS
     * @param courseId
     * @param title
     * @param description
     * @param examFile
     * @param startTime
     * @param endTime
     * @return
     */
    Exam create(Long courseId, String title, String description, FileModel examFile, Time startTime, Time endTime);

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
    List<Exam> list(Long courseId);

    /**
     * Attempts to get an exam given an id
     * @param examId of the exam to be retrieved
     * @return the exam corresponding to the given id if it exists, null otherwise
     */
    Optional<Exam> findById(Long examId);
}
