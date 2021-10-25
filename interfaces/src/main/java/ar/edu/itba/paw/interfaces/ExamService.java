package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.ExamModel;
import ar.edu.itba.paw.models.FileModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExamService {

    /**
     * Attempts to persist an exam and its file in the database
     *
     * @param title of the exam
     * @param instructions of the exam
     * @param size   of the file
     * @param name   of the file
     * @param file   representation in byte array
     * @param course where the file belongs to
     * @param startDate of the beginning of the exam
     * @param finishDate of the end of the exam
     * @return the exam if it was successfully added
     */
    ExamModel create(String title, String instructions, Long size, String name, byte[] file, Course course, LocalDateTime startDate, LocalDateTime finishDate);

    /**
     * Attempts to persist an exam entry in the database
     *
     * @param title of the exam
     * @param instructions of the exam
     * @param file exam file
     * @param startDate of the beginning of the exam
     * @param finishDate of the end of the exam
     * @return the exam if it was successfully added
     */
    ExamModel create(String title, String instructions, FileModel file, LocalDateTime startDate, LocalDateTime finishDate);

    /**
     * Attempts to update an exam
     *
     * @param examId of the exam to be modified
     * @param exam   modified exam
     * @return true if the exam was successfully updated, false otherwise
     */
    boolean update(Long examId, ExamModel exam);

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
    List<ExamModel> list(Long courseId);

    /**
     * Attempts to get an exam given an id
     * @param examId of the exam to be retrieved
     * @return the exam corresponding to the given id if it exists, null otherwise
     */
    Optional<ExamModel> findById(Long examId);
}
