package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.ExamDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.Subject;
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
@Sql("classpath:populators/exam_populator.sql")
@Rollback
@Transactional
public class ExamDaoJpaTest extends BasicPopulator {
    @Autowired
    private ExamDao examDao;

    private final Long CORRECTED_EXAM_ID = 2L;
    private final Long DB_STUDENT_ID = 1337L;


    @Test
    public void testCreate() {
        FileModel examFile = createFileModelObject(FILE_PATH, FILE_ID);
        LocalDateTime startTime = LocalDateTime.MIN;
        LocalDateTime endTime = LocalDateTime.now();
        Exam exam = examDao.create(COURSE_ID, EXAM_TITLE, EXAM_DESCRIPTION, examFile, startTime, endTime);
        assertNotNull(exam);
    }

    @Test
    public void testDelete() {
        assertTrue(examDao.delete(EXAM_ID));
    }

    @Test
    public void testUpdate() {
        FileModel examFile = createFileModelObject(FILE_PATH, FILE_ID);
        Exam exam = new Exam.Builder()
                .withCourse(new Course(COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, new Subject(SUBJECT_CODE, SUBJECT_NAME)))
                .withStartTime(LocalDateTime.MIN)
                .withEndTime(LocalDateTime.now())
                .withTitle(EXAM_TITLE)
                .withDescription(EXAM_DESCRIPTION)
                .withExamFile(examFile)
                .build();
        assertTrue(examDao.update(EXAM_ID, exam));
    }

    @Test
    public void testListByCourse() {
        List<Exam> list = examDao.listByCourse(COURSE_ID);

        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    public void testFindById() {
        final Optional<Exam> examOptional = examDao.findById(EXAM_ID);
        assertNotNull(examOptional);
        assertTrue(examOptional.isPresent());
        assertEquals(EXAM_ID, examOptional.get().getExamId());
    }

    @Test
    public void testGetAverageScoreInCourse() {
        Double score = examDao.getAverageScoreOfExam(CORRECTED_EXAM_ID);

        assertEquals(Double.valueOf(10), score);
    }

    @Test
    public void testGetUnResolvedExams() {
        List<Exam> exams = examDao.getUnresolvedExams(DB_STUDENT_ID, COURSE_ID);

        assertTrue(exams.isEmpty());
    }

    @Test
    public void testGetTotalResolvedByExam() {
        Long totalResolved = examDao.getTotalResolvedByExam(EXAM_ID);

        assertEquals(Long.valueOf(1L), totalResolved);
    }

    @Test
    public void testGetResolvedExams() {
        List<Exam> exams = examDao.getResolvedExams(DB_STUDENT_ID, COURSE_ID);

        assertFalse(exams.isEmpty());
        assertEquals(2, exams.size());
    }
}
