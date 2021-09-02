import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.services.AnnouncementServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnouncementServiceImplTest {
    private static final long ANNOUNCEMENT_ID = 1;
    private static final long COURSE_ID = 1;
    private static final long TEACHER_ID = 1;
    private static final Date ANNOUNCEMENT_DATE = new Date(2323223232L);
    private static final String ANNOUNCEMENT_TITLE = "Unit Testing";
    private static final String ANNOUNCEMENT_CONTENT = "Rocks! (or not)";

    private Announcement getMockAnnouncement() {
        return new Announcement(ANNOUNCEMENT_ID, COURSE_ID, TEACHER_ID,ANNOUNCEMENT_DATE,
                ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT);
    }

    @InjectMocks
    private AnnouncementServiceImpl announcementService = new AnnouncementServiceImpl();

    @Mock
    private AnnouncementDao mockDao;

    @Test
    public void testCreateAnnouncement() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.create(eq(announcement))).thenReturn(true);
        final boolean announcementCreateResult = announcementService.create(announcement);
        Assert.assertTrue(announcementCreateResult);
    }

    @Test
    public void testFindByCourseId() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.getById(eq((int)COURSE_ID))).thenReturn(Optional.of(announcement));
        final Optional<Announcement> queriedAnnouncement = announcementService.getById((int)COURSE_ID);

        Assert.assertTrue(queriedAnnouncement.isPresent());
        Assert.assertEquals(ANNOUNCEMENT_ID, queriedAnnouncement.get().getAnnouncementId());
    }

    @Test(expected =  RuntimeException.class)
    public void testCreateAnnouncementDuplicate() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.create(eq(announcement))).thenThrow(new RuntimeException());
        boolean announcementCreateResult = announcementService.create(announcement);
        Assert.fail("Should have thrown runtime exception for duplicate announcement creation");
    }


}
