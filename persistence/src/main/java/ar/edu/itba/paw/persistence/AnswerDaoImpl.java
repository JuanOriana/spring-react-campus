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
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class AnswerDaoImpl extends BasePaginationDaoImpl<AnswerDao> implements AnswerDao {

    @Transactional
    @Override
    public Answer create(Exam exam, User student, User teacher, FileModel answerFile, Float score, String corrections, Time deliverdTime) {
        final Answer answer = new Answer(exam, deliverdTime, student, teacher, answerFile, score, corrections);
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
        TypedQuery<Integer> totalResolvedTypedQuery = em.createQuery("SELECT COUNT(DISTINCT a.student.userid) FROM Answer a WHERE a.exam.examid = :examid", Integer.class);
        totalResolvedTypedQuery.setParameter("examid", examId);
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
    public void uncorrectExam(Long answerId) {
        correctExam(answerId, null, null);
    }

    @Override
    public List<Exam> getCorrectedExams(Long courseId) {
        TypedQuery<Exam> correctedExamsTypedQuery = em.createQuery("SELECT answer.exam FROM Answer answer WHERE answer.exam.course.courseId = :courseId AND answer.score IS NOT NULL", Exam.class);
        correctedExamsTypedQuery.setParameter("courseId", courseId);
        return correctedExamsTypedQuery.getResultList();
    }

    @Override
    public List<Exam> getNotCorrectedExams(Long courseId) {
        TypedQuery<Exam> correctedExamsTypedQuery = em.createQuery("SELECT answer.exam FROM Answer answer WHERE answer.exam.course.courseId = :courseId AND answer.score IS NULL", Exam.class);
        correctedExamsTypedQuery.setParameter("courseId", courseId);
        return correctedExamsTypedQuery.getResultList();
    }
}
