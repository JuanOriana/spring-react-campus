package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AnswerDao;
import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answersDao;

    @Override
    public Answer create(Exam exam, User student, FileModel answerFile, Time deliverdTime) {
        return  answersDao.create(exam, student, null, answerFile, null, null, deliverdTime);
    }

    @Override
    public boolean update(Long answerId, Answer answer) {
        return answersDao.update(answerId, answer);
    }

    @Override
    public boolean delete(Long answerId) {
        return answersDao.delete(answerId);
    }

    @Override
    public Optional<Answer> findById(Long answerId) {
        return answersDao.findById(answerId);
    }
}
