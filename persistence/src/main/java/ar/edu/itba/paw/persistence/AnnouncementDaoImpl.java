package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    @Override
    public boolean create(Announcement announcement) {
        return false;
    }

    @Override
    public boolean update(int id, Announcement announcement) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Announcement> list() {
        return null;
    }

    @Override
    public List<Announcement> listByCourse(int courseId) {
        return null;
    }

    @Override
    public Announcement getById(int id) {
        return null;
    }
}
