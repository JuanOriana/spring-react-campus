package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnswersDao;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Optional;

@Primary
@Repository
public class AnswerDao extends BasePaginationDaoImpl<AnswerDao> implements AnswersDao {

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
        if(!dbAnswer.isPresent()) return false;
        dbAnswer.get().merge(answer);
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long answerId) {
        Optional<Answer> dbAnswer = findById(answerId);
        if(!dbAnswer.isPresent()) return false;
        em.remove(dbAnswer.get());
        return true;
    }

    @Transactional
    @Override
    public Optional<Answer> findById(Long answerId) {
        return Optional.ofNullable(em.find(Answer.class, answerId));
    }
}
