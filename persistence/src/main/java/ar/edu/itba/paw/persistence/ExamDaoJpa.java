package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ExamDao;
import ar.edu.itba.paw.models.ExamModel;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class ExamDaoJpa extends BasePaginationDaoImpl<ExamModel> implements ExamDao {

    @Transactional
    @Override
    public ExamModel create(String title, String instructions, FileModel file, LocalDateTime startDate, LocalDateTime finishDate) {
        final ExamModel examModel = new ExamModel.Builder()
                .withTitle(title)
                .withInstructions(instructions)
                .withFile(file)
                .withStartDate(startDate)
                .withFinishDate(finishDate)
                .build();
        em.persist(examModel);
        return examModel;
    }

    @Transactional
    @Override
    public boolean update(Long examId, ExamModel exam) {
        Optional<ExamModel> dbExam = findById(examId);
        if (!dbExam.isPresent()) return false;
        dbExam.get().merge(exam);
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long examId) {
        Optional<ExamModel> dbExam = findById(examId);
        if (!dbExam.isPresent()) return false;
        em.remove(dbExam.get());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExamModel> list(Long courseId) {
        TypedQuery<ExamModel> listExamsOfCourse = em.createQuery("SELECT e FROM ExamModel e WHERE e.file.course.courseId = :courseId", ExamModel.class);
        listExamsOfCourse.setParameter("courseId", courseId);
        return listExamsOfCourse.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ExamModel> findById(Long examId) {
        return Optional.ofNullable(em.find(ExamModel.class, examId));
    }
}
