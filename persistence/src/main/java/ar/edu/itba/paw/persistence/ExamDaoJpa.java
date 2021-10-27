package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ExamDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class ExamDaoJpa extends BasePaginationDaoImpl<Exam> implements ExamDao {

    @Transactional
    @Override
    public Exam create(Long courseId, String title, String description, FileModel examFile, FileModel answersFile, Time startTime, Time endTime) {
        final Exam exam = new Exam.Builder()
                .withCourse(new Course(courseId, null, null, null, null))
                .withTitle(title)
                .withDescription(description)
                .withExamFile(examFile)
                .withStartTime(startTime)
                .withEndTime(endTime).build();
        em.persist(exam);
        return exam;
    }

    @Transactional
    @Override
    public boolean update(Long examId, Exam exam) {
        Optional<Exam> dbExam = findById(examId);
        if (!dbExam.isPresent()) return false;
        dbExam.get().merge(exam);
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long examId) {
        Optional<Exam> dbExam = findById(examId);
        if (!dbExam.isPresent()) return false;
        em.remove(dbExam.get());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Exam> listByCourse(Long courseId) {
        TypedQuery<Exam> listExamsOfCourse = em.createQuery("SELECT e FROM Exam e WHERE e.course.courseId = :courseId", Exam.class);
        listExamsOfCourse.setParameter("courseId", courseId);
        return listExamsOfCourse.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Exam> findById(Long examId) {
        return Optional.ofNullable(em.find(Exam.class, examId));
    }
}
