package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.ExamModel;
import ar.edu.itba.paw.models.FileModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExamDao {

    ExamModel create(String title, String instructions, FileModel file, LocalDateTime startDate, LocalDateTime finishDate);

    boolean update(Long examId, ExamModel exam);

    boolean delete(Long examId);

    List<ExamModel> list(Long courseId);

    Optional<ExamModel> findById(Long examId);

}
