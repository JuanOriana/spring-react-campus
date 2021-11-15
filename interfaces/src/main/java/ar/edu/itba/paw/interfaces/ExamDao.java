package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ExamDao {

    Exam create(Long courseId, String title, String description, FileModel examFile, LocalDateTime startTime, LocalDateTime endTime);

    boolean update(Long examId, Exam exam);

    boolean delete(Long examId);

    List<Exam> listByCourse(Long courseId);

    Optional<Exam> findById(Long examId);

    List<Exam> getUnresolvedExams(Long studentId, Long courseId);

    List<Exam> getResolvedExams(Long studentId, Long courseId);

    Long getTotalResolvedByExam(Long examId);

    Double getAverageScoreOfExam(Long examId);

}
