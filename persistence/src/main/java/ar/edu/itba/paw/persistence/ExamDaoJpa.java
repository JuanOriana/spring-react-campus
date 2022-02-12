package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ExamDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class ExamDaoJpa extends BasePaginationDaoImpl<Exam> implements ExamDao {


    @Override
    public Exam create(Long courseId, String title, String description, FileModel examFile, LocalDateTime startTime, LocalDateTime endTime) {
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


    @Override
    public boolean update(Long examId, Exam exam) {
        Optional<Exam> dbExam = findById(examId);
        if (!dbExam.isPresent()) return false;
        dbExam.get().merge(exam);
        return true;
    }


    @Override
    public boolean delete(Long examId) {
        Optional<Exam> dbExam = findById(examId);
        if (!dbExam.isPresent()) return false;
        em.remove(dbExam.get());
        return true;
    }


    @Override
    public List<Exam> listByCourse(Long courseId) {
        TypedQuery<Exam> listExamsOfCourse = em.createQuery("SELECT e FROM Exam e WHERE e.course.courseId = :courseId", Exam.class);
        listExamsOfCourse.setParameter("courseId", courseId);
        return listExamsOfCourse.getResultList();
    }


    @Override
    public Optional<Exam> findById(Long examId) {
        return Optional.ofNullable(em.find(Exam.class, examId));
    }

    @Override
    public List<Exam> getUnresolvedExams(Long studentId, Long courseId) {
        TypedQuery<Exam> unresolvedExamsTypedQuery = em.createQuery("SELECT DISTINCT(answer.exam) FROM Answer answer WHERE answer.student.userId = :studentId AND answer.exam.course.courseId = :courseId AND answer.deliveredDate IS NULL AND answer.exam.endTime >= :nowTime AND answer.exam.startTime <= :nowTime", Exam.class);
        unresolvedExamsTypedQuery.setParameter("studentId", studentId);
        unresolvedExamsTypedQuery.setParameter("courseId", courseId);
        unresolvedExamsTypedQuery.setParameter("nowTime", LocalDateTime.now());
        return unresolvedExamsTypedQuery.getResultList();
    }

    @Override
    public List<Exam> getResolvedExams(Long studentId, Long courseId) {
        TypedQuery<Exam> resolverExamsTypedQuery = em.createQuery("SELECT DISTINCT(answer.exam) FROM Answer answer WHERE answer.student.userId = :studentId AND answer.exam.course.courseId = :courseId AND (answer.deliveredDate IS NOT NULL OR answer.exam.endTime < :nowTime)", Exam.class);
        resolverExamsTypedQuery.setParameter("studentId", studentId);
        resolverExamsTypedQuery.setParameter("courseId", courseId);
        resolverExamsTypedQuery.setParameter("nowTime", LocalDateTime.now());
        return resolverExamsTypedQuery.getResultList();
    }


    @Override
    public Long getTotalResolvedByExam(Long examId) {
        TypedQuery<Long> totalResolvedTypedQuery = em.createQuery("SELECT COUNT(DISTINCT a.student.userId) FROM Answer a WHERE a.exam.examId = :examId", Long.class);
        totalResolvedTypedQuery.setParameter("examId", examId);
        return totalResolvedTypedQuery.getSingleResult();
    }

    @Override
    public Double getAverageScoreOfExam(Long examId) {
        TypedQuery<Double> averageQuery = em.createQuery("SELECT AVG(ans.score) FROM Answer ans WHERE ans.exam.examId =:examId AND ans.score IS NOT NULL GROUP BY ans.exam.examId", Double.class);
        averageQuery.setParameter("examId", examId);
        List<Double> doubleList = averageQuery.getResultList();
        if (doubleList.isEmpty()){
            return 0.0;
        }
        return doubleList.get(0);
    }
}
