import ar.edu.itba.paw.interfaces.ExamDao;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.services.ExamServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExamServiceImplTest {

    @InjectMocks
    private final ExamServiceImpl examService = new ExamServiceImpl();

    @Mock
    private ExamDao examDao;


    @Test
    public void testGetExamsAndTotals(){
       Exam exam = BaseServiceTestUtil.getMockExam();

//       when(examDao.getExam)
    }


    // TODO
//    @Test
//    public void testGetExamsAndTotals(){
//        Map<Exam,Pair<Long,Long>> examPairMap = answerDao.getExamsAndTotals(COURSE_ID);
//
//        assertFalse(examPairMap.isEmpty());
//        Pair<Long,Long> pair =examPairMap.get(exam);
//        assertEquals(Long.valueOf(1L), pair.getKey());
//        assertEquals(Long.valueOf(0L), pair.getValue());
//
//    }
//    @Test
//    public void testGetExamsAverage(){
//        Map<Exam,Double> examDoubleMap = answerDao.getExamsAverage(COURSE_ID);
//
//        assertFalse(examDoubleMap.isEmpty());
//        assertEquals(Double.valueOf(0), examDoubleMap.get(exam));
//
//    }
}
