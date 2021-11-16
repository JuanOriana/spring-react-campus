package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnswerDao;
import ar.edu.itba.paw.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/answer_populator.sql")
@Rollback
@Transactional
public class AnswerDaoJpaTest extends BasicPopulator {

    @Autowired
    private AnswerDao answerDao;

    private final Long DB_STUDENT_ID = 1337L;
    private final Long DB_FILE_ID = 1337L;
    private final FileModel answerFile = createFileModelObject(FILE_PATH, FILE_ID);
    private final Subject subject = new Subject(SUBJECT_CODE, SUBJECT_NAME);
    private final Course course = new Course.Builder().withCourseId(COURSE_ID).withYear(COURSE_YEAR).withQuarter(COURSE_QUARTER).withBoard(COURSE_BOARD).withSubject(subject).build();
    private final Exam exam = new Exam.Builder().withExamId(EXAM_ID).withCourse(course).withTitle(EXAM_TITLE).withDescription(EXAM_DESCRIPTION).withExamFile(answerFile).withStartTime(LocalDateTime.MIN).withEndTime(LocalDateTime.MAX).build();
    private final User student = new User.Builder().withUserId(DB_STUDENT_ID).withUsername(USER_USERNAME).withName(USER_NAME).withSurname(USER_SURNAME).withEmail(USER_EMAIL).withFileNumber(USER_FILE_NUMBER).build();
    private final Long DB_ANSWER_ID = 1L;
    private final Long CORRECTED_EXAM_ID = 2L;

    @Test
    public void testCreate() {
        final Long studentId = USER_ID;
        final Long teacherId = 999L;
        Answer answer = answerDao.create(exam, studentId, teacherId, answerFile, null, null, LocalDateTime.MIN);
        assertNotNull(answer);
    }

    @Test
    public void testDidUserDeliver() {
        assertFalse(answerDao.didUserDeliver(EXAM_ID, DB_STUDENT_ID));
    }

    @Test
    public void testUpdate() {
        Answer answer = new Answer.Builder()
                .withAnswerId(DB_ANSWER_ID)
                .withDeliveredDate(LocalDateTime.now())
                .withStudent(student)
                .withExam(exam)
                .withScore(9.5F)
                .build();
        assertTrue(answerDao.update(DB_ANSWER_ID, answer));
    }

    @Test
    public void testEmptyAnswer() {
        Answer answer = new Answer.Builder()
                .withAnswerId(DB_ANSWER_ID)
                .withDeliveredDate(LocalDateTime.now())
                .withStudent(student)
                .withExam(exam)
                .withAnswerFile(createFileModelObject(FILE_PATH, DB_FILE_ID))
                .build();

        Answer dbAnswer = answerDao.updateEmptyAnswer(EXAM_ID, student, null, answer);
        assertNotNull(dbAnswer);
        assertEquals(DB_FILE_ID, dbAnswer.getAnswerFile().getFileId());
    }

    @Test
    public void testDelete() {
        assertTrue(answerDao.delete(DB_ANSWER_ID));
    }

    @Test
    public void testFindById() {
        Optional<Answer> answerOptional = answerDao.findById(DB_ANSWER_ID);
        assertTrue(answerOptional.isPresent());
        assertEquals(EXAM_ID, answerOptional.get().getExam().getExamId());
    }


    @Test
    public void testGetFilteredCorrectedAnswers(){
        final Long correctedAnswerId = 2L;
        CampusPage<Answer> campusPage = answerDao.getFilteredAnswers(CORRECTED_EXAM_ID,"corrected",new CampusPageRequest(1, 10) );
        assertNotNull(campusPage);

        assertFalse(campusPage.getContent().isEmpty());

        assertEquals(1, campusPage.getContent().size());
        assertEquals(correctedAnswerId,campusPage.getContent().get(0).getAnswerId());
    }

    @Test
    public void testGetFilteredNotCorrectedAnswers(){
        CampusPage<Answer> campusPage = answerDao.getFilteredAnswers(CORRECTED_EXAM_ID,"not corrected",new CampusPageRequest(1, 10) );
        assertNotNull(campusPage);

        assertFalse(campusPage.getContent().isEmpty());

        assertEquals(1, campusPage.getContent().size());

    }



    @Test
    public void testGetMarks() {
        List<Answer> answerList = answerDao.getMarks(DB_STUDENT_ID, COURSE_ID);

        assertFalse(answerList.isEmpty());
        assertEquals(EXAM_ID, answerList.get(0).getExam().getExamId());
    }

    @Test
    public void testGetMarksEmpty() {
        List<Answer> answerList = answerDao.getMarks(USER_ID_INEXISTENCE, COURSE_ID);

        assertTrue(answerList.isEmpty());
    }

    @Test
    public void testGetTotalAnswers() {
        Long totalAnswers = answerDao.getTotalAnswers(EXAM_ID);

        assertEquals(Long.valueOf(1L), totalAnswers);
    }

    @Test
    public void testGetTotalCorrectedAnswersZero() {
        Long totalAnswers = answerDao.getTotalCorrectedAnswers(EXAM_ID);

        assertEquals(Long.valueOf(0L), totalAnswers);
    }

    @Test
    public void testGetTotalCorrectedAnswers() {
        Long totalAnswers = answerDao.getTotalCorrectedAnswers(CORRECTED_EXAM_ID);

        assertEquals(Long.valueOf(1L), totalAnswers);
    }


    @Test
    public void testGetAverageOfUserInCourse() {
        Double average = answerDao.getAverageOfUserInCourse(student.getUserId(), COURSE_ID);

        assertEquals(Double.valueOf(10), average);
    }


}
