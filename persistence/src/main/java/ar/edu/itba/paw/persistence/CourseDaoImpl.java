package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CourseDaoImpl implements CourseDao {
    // Only for testing, replace with proper db implementation
    private Map<Integer, Course> courses = new ConcurrentHashMap<Integer, Course>();

    public CourseDaoImpl() {
        // Only for testing, replace with proper db implementation
        courses.put(1, new Course(1, "Matematica Discreta"));
        courses.put(2, new Course(2, "Formacion General I"));
        courses.put(3, new Course(3, "Programacion Orientada a Objetos"));
        courses.put(4, new Course(4, "Algebra"));
    }

    @Override
    public boolean create(Course course) {
        return false;
    }

    @Override
    public boolean update(int id, Course course) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Course> list() {
        // Only for testing, replace with proper db implementation
        return new ArrayList<Course>(this.courses.values());
    }

    @Override
    public Optional<Course> getById(int id) {
        // Only for testing, replace with proper db implementation
        return Optional.ofNullable(this.courses.get(id));
    }
}
