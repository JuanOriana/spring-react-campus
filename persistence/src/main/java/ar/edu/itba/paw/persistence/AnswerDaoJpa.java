package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnswerDao;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class AnswerDaoJpa extends BasePaginationDaoImpl<AnswerDao> implements AnswerDao {

    @Transactional
    @Override
    public Answer create(Exam exam, Long studentId, Long teacherId, FileModel answerFile, Float score, String corrections, LocalDateTime deliveredTime) {
        User student = em.find(User.class, studentId);
        User teacher = null;
        if(teacherId != null) {
            teacher = em.find(User.class, teacherId);
        }
        final Answer answer = new Answer(exam, deliveredTime, student, teacher, answerFile, score, corrections);
        em.persist(answer);
        return answer;
    }

    @Transactional
    @Override
    public boolean update(Long answerId, Answer answer) {
        Optional<Answer> dbAnswer = findById(answerId);
        if (!dbAnswer.isPresent()) return false;
        dbAnswer.get().merge(answer);
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long answerId) {
        Optional<Answer> dbAnswer = findById(answerId);
        if (!dbAnswer.isPresent()) return false;
        em.remove(dbAnswer.get());
        return true;
    }


    @Override
    public Optional<Answer> findById(Long answerId) {
        return Optional.ofNullable(em.find(Answer.class, answerId));
    }

    @Override
    public Integer getTotalResolvedByExam(Long examId) {
        TypedQuery<Integer> totalResolvedTypedQuery = em.createQuery("SELECT COUNT(DISTINCT a.student.userId) FROM Answer a WHERE a.exam.examId = :examId", Integer.class);
        totalResolvedTypedQuery.setParameter("examId", examId);
        return totalResolvedTypedQuery.getSingleResult();
    }

    @Transactional
    @Override
    public void correctExam(Long answerId, User teacher, Float score) {
        Optional<Answer> dbAnswer = findById(answerId);

        if (dbAnswer.isPresent()) {
            dbAnswer.get().setScore(score);
            dbAnswer.get().setTeacher(teacher);
        } else {
            // TODO: Throw exception and log error
        }
    }

    @Override
    public void undoExamCorrection(Long answerId) {
        correctExam(answerId, null, null);
    }

    @Override
    public List<Answer> getCorrectedAnswers(Long examId) {
        TypedQuery<Answer> correctedExamsTypedQuery = em.createQuery("SELECT answer FROM Answer answer WHERE answer.exam.examId = :examId AND answer.score IS NOT NULL", Answer.class);
        correctedExamsTypedQuery.setParameter("examId", examId);
        return correctedExamsTypedQuery.getResultList();
    }

    @Override
    public List<Answer> getNotCorrectedAnswers(Long courseId) {
        TypedQuery<Answer> correctedExamsTypedQuery = em.createQuery("SELECT answer FROM Answer answer WHERE answer.exam.course.courseId = :courseId AND answer.score IS NULL", Answer.class);
        correctedExamsTypedQuery.setParameter("courseId", courseId);
        return correctedExamsTypedQuery.getResultList();
    }

    @Override
    public Integer getTotalAnswers(Long examId) {
        TypedQuery<Integer> totalAnswersTypedQuery = em.createQuery("SELECT COUNT(DISTINCT answer.student.userId) FROM Answer answer WHERE answer.exam.examId = :examId", Integer.class);
        totalAnswersTypedQuery.setParameter("examId", examId);
        return totalAnswersTypedQuery.getSingleResult();
    }

    @Override
    public Integer getTotalCorrectedAnswers(Long examId) {
        TypedQuery<Integer> totalAnswersTypedQuery = em.createQuery("SELECT COUNT(DISTINCT answer.student.userId) FROM Answer answer WHERE answer.exam.examId = :examId AND answer.score IS NOT NULL", Integer.class);
        totalAnswersTypedQuery.setParameter("examId", examId);
        return totalAnswersTypedQuery.getSingleResult();
    }


    @Override
    public List<Exam> getResolvedExams(Long studentId,Long courseId) {
        TypedQuery<Exam> resolverExamsTypedQuery = em.createQuery("SELECT answer.exam FROM Answer answer WHERE answer.student.userId = :studentId AND answer.exam.course.courseId = :courseId", Exam.class);
        resolverExamsTypedQuery.setParameter("studentId", studentId);
        resolverExamsTypedQuery.setParameter("courseId", courseId);
        return resolverExamsTypedQuery.getResultList();
    }

    @Override
    public List<Exam> getUnresolvedExams(Long studentId, Long courseId) {
        TypedQuery<Exam> unresolvedExamsTypedQuery = em.createQuery("SELECT exam FROM Exam exam WHERE exam NOT IN (SELECT answer.exam FROM Answer answer WHERE answer.student.userId = :studentId) AND exam.course.courseId = :courseId", Exam.class);
        unresolvedExamsTypedQuery.setParameter("studentId", studentId);
        unresolvedExamsTypedQuery.setParameter( "courseId", courseId);

        return unresolvedExamsTypedQuery.getResultList();
    }


}
