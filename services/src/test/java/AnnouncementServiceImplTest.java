import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.CampusPageRequest;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import ar.edu.itba.paw.services.AnnouncementServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnouncementServiceImplTest {

    @Mock
    private AnnouncementDao announcementDao;

    @InjectMocks
    private final AnnouncementServiceImpl announcementService = new AnnouncementServiceImpl();

    @Test(expected = PaginationArgumentException.class)
    public void testListByUserPaginationExceptionNegativePage() {
        Announcement announcement = BaseServiceTestUtil.getMockAnnouncement();
        CampusPageRequest pageRequest = new CampusPageRequest(BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_SIZE);
        when(announcementDao.listByUser(BaseServiceTestUtil.USER_ID, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Announcement>() {{
                    add(announcement);
                }},
                        BaseServiceTestUtil.PAGE_SIZE, BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_TOTAL));
        announcementService.listByUser(BaseServiceTestUtil.USER_ID, BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }

    @Test(expected = PaginationArgumentException.class)
    public void testListByUserPaginationExceptionNegativePageSize() {
        Announcement announcement = BaseServiceTestUtil.getMockAnnouncement();
        CampusPageRequest pageRequest = new CampusPageRequest(BaseServiceTestUtil.PAGE, BaseServiceTestUtil.NEGATIVE_PAGE_SIZE);
        when(announcementDao.listByUser(BaseServiceTestUtil.USER_ID, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Announcement>() {{
                    add(announcement);
                }},
                        BaseServiceTestUtil.NEGATIVE_PAGE_SIZE, BaseServiceTestUtil.PAGE, BaseServiceTestUtil.PAGE_TOTAL));
        announcementService.listByUser(BaseServiceTestUtil.USER_ID, BaseServiceTestUtil.NEGATIVE_PAGE_SIZE, BaseServiceTestUtil.PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }

    @Test(expected = PaginationArgumentException.class)
    public void testListByCoursePaginationExceptionNegativePage() {
        Announcement announcement = BaseServiceTestUtil.getMockAnnouncement();
        CampusPageRequest pageRequest = new CampusPageRequest(BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_SIZE);
        when(announcementDao.listByCourse(BaseServiceTestUtil.USER_ID, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Announcement>() {{
                    add(announcement);
                }},
                        BaseServiceTestUtil.PAGE_SIZE, BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_TOTAL));
        announcementService.listByCourse(BaseServiceTestUtil.USER_ID, BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }

    @Test(expected = PaginationArgumentException.class)
    public void testListByCoursePaginationExceptionNegativePageSize() {
        Announcement announcement = BaseServiceTestUtil.getMockAnnouncement();
        CampusPageRequest pageRequest = new CampusPageRequest(BaseServiceTestUtil.PAGE, BaseServiceTestUtil.NEGATIVE_PAGE_SIZE);
        when(announcementDao.listByCourse(BaseServiceTestUtil.USER_ID, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Announcement>() {{
                    add(announcement);
                }},
                        BaseServiceTestUtil.NEGATIVE_PAGE_SIZE, BaseServiceTestUtil.PAGE, BaseServiceTestUtil.PAGE_TOTAL));
        announcementService.listByCourse(BaseServiceTestUtil.USER_ID, BaseServiceTestUtil.PAGE, BaseServiceTestUtil.NEGATIVE_PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }


}
