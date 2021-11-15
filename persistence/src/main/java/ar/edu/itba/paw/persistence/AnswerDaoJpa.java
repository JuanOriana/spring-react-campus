package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnswerDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.AnswerNotFoundException;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class AnswerDaoJpa extends BasePaginationDaoImpl<Answer> implements AnswerDao {

    @Override
    public Answer create(Exam exam, Long studentId, Long teacherId, FileModel answerFile, Float score, String corrections, LocalDateTime deliveredTime) {
        User student = em.getReference(User.class, studentId);
        User teacher = null;
        if (teacherId != null) {
            teacher = em.find(User.class, teacherId);
        }
        final Answer answer = new Answer(exam, deliveredTime, student, teacher, answerFile, score, corrections);
        em.persist(answer);
        return answer;
    }

    @Override
    public void createEmptyAnswers(Exam exam, List<User> students) {
        Answer answer;
        for (User student : students) {
            answer = new Answer(exam, null, student, null, null, null, null);
            em.persist(answer);
        }
    }

    @Override
    public boolean didUserDeliver(Long examId, Long userId) {
        TypedQuery<Answer> answerTypedQuery = em.createQuery("SELECT a FROM Answer a WHERE a.student.userId = :studentId AND a.exam.examId = :examId", Answer.class);
        answerTypedQuery.setParameter("examId", examId);
        answerTypedQuery.setParameter("studentId", userId);
        return answerTypedQuery.getResultList().isEmpty();
    }

    @Override
    public boolean update(Long answerId, Answer answer) {
        Optional<Answer> dbAnswer = findById(answerId);
        if (!dbAnswer.isPresent()) return false;
        dbAnswer.get().merge(answer);
        em.flush();
        return true;
    }

    @Override
    public Answer updateEmptyAnswer(Long examId, User student, Long teacherId, Answer answer) {
        TypedQuery<Answer> getEmptyAnswer = em.createQuery("SELECT a FROM Answer a WHERE a.student.userId = :studentId AND a.exam.examId = :examId", Answer.class);
        getEmptyAnswer.setParameter("examId", examId);
        getEmptyAnswer.setParameter("studentId", student.getUserId());
        Answer oldAnswer = getEmptyAnswer.getSingleResult();
        if (oldAnswer != null) {
            this.update(oldAnswer.getAnswerId(), answer);
        }

        return oldAnswer;
    }

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
    public void correctExam(Long answerId, User teacher, Float score, String corrections) {
        Optional<Answer> dbAnswer = findById(answerId);

        if (dbAnswer.isPresent()) {
            dbAnswer.get().setScore(score);
            dbAnswer.get().setTeacher(teacher);
            dbAnswer.get().setCorrections(corrections);
        } else {
            throw new ExamNotFoundException();
        }
    }

    @Override
    public void undoExamCorrection(Long answerId) {
        Optional<Answer> answer = findById(answerId);
        if (!answer.isPresent()) throw new AnswerNotFoundException();
        answer.get().setCorrections(null);
        answer.get().setScore(null);
    }

    @Override
    public CampusPage<Answer> getFilteredAnswers(Long examId, String filter, CampusPageRequest pageRequest) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("examId", examId);
        String queryConditional = " ";
        String mappingQueryConditional = " ";
        if (filter.equalsIgnoreCase("corrected")) {
            queryConditional = " AND score IS NOT NULL ";
            mappingQueryConditional = " AND a.score IS NOT NULL ";
        } else if (filter.equalsIgnoreCase("not corrected")) {
            queryConditional = " AND score IS NULL ";
            mappingQueryConditional = " AND a.score IS NULL ";
        }
        String query = "SELECT answerId FROM answers WHERE examId = :examId" + queryConditional + "ORDER BY deliveredDate ASC,score DESC";
        String mappingQuery = "SELECT a FROM Answer a WHERE a.answerId IN (:ids)" + mappingQueryConditional + "ORDER BY deliveredDate ASC,a.score DESC";
        return listBy(properties, query, mappingQuery, pageRequest, Answer.class);
    }

    @Override
    public List<Answer> getMarks(Long userId, Long courseId) {
        TypedQuery<Answer> answerTypedQuery = em.createQuery("SELECT a FROM Answer a WHERE a.student.userId = :userId AND a.exam.course.courseId = :courseId AND (a.deliveredDate IS NOT NULL OR a.exam.endTime < :today )", Answer.class);
        answerTypedQuery.setParameter("userId", userId);
        answerTypedQuery.setParameter("courseId", courseId);
        answerTypedQuery.setParameter("today", LocalDateTime.now());
        return answerTypedQuery.getResultList();
    }


    @Override
    public List<Answer> getCorrectedAnswers(Long examId) {
        TypedQuery<Answer> correctedExamsTypedQuery = em.createQuery("SELECT answer FROM Answer answer WHERE answer.exam.examId = :examId AND answer.score IS NOT NULL", Answer.class);
        correctedExamsTypedQuery.setParameter("examId", examId);
        return correctedExamsTypedQuery.getResultList();
    }

    @Override
    public List<Answer> getNotCorrectedAnswers(Long examId) {
        TypedQuery<Answer> correctedExamsTypedQuery = em.createQuery("SELECT answer FROM Answer answer WHERE answer.exam.examId = :examId AND answer.score IS NULL ORDER BY answer.deliveredDate ASC", Answer.class);
        correctedExamsTypedQuery.setParameter("examId", examId);
        return correctedExamsTypedQuery.getResultList();
    }

    @Override
    public Long getTotalAnswers(Long examId) {
        TypedQuery<Long> totalAnswersTypedQuery = em.createQuery("SELECT COUNT(DISTINCT answer.student.userId) FROM Answer answer WHERE answer.exam.examId = :examId", Long.class);
        totalAnswersTypedQuery.setParameter("examId", examId);
        return totalAnswersTypedQuery.getSingleResult();
    }

    @Override
    public Long getTotalCorrectedAnswers(Long examId) {
        TypedQuery<Long> totalAnswersTypedQuery = em.createQuery("SELECT COUNT(DISTINCT answer.student.userId) FROM Answer answer WHERE answer.exam.examId = :examId AND answer.score IS NOT NULL", Long.class);
        totalAnswersTypedQuery.setParameter("examId", examId);
        return totalAnswersTypedQuery.getSingleResult();
    }


    @Override
    public Double getAverageOfUserInCourse(Long studentId, Long courseId) {
        TypedQuery<Double> averageQuery = em.createQuery("SELECT COALESCE(AVG(ans.score),0) FROM Answer ans WHERE ans.score IS NOT NULL AND ans.exam.course.courseId =:courseId AND ans.student.userId =:studentId", Double.class);
        averageQuery.setParameter("courseId", courseId);
        averageQuery.setParameter("studentId", studentId);

        return averageQuery.getSingleResult();

    }


}
