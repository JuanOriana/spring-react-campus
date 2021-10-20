package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_to_course")
public class Enrollment {

    @EmbeddedId
    private Pk primaryKey;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable = false, updatable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    /*Default */ Enrollment() {
        //Just for hibernate
    }

    public Enrollment(User user, Course course, Role role) {
        this.user = user;
        this.course = course;
        this.role = role;
        this.primaryKey = new Pk(course.getCourseId(), user.getUserId());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    private static class Pk implements Serializable {
        @Column(nullable = false, updatable = false)
        private Long courseId;

        @Column(nullable = false, updatable = false)
        private Long userId;

        /* Default */ Pk() {
            // Just for Hibernate
        }

        public Pk(Long courseId, Long userId) {
            this.courseId = courseId;
            this.userId = userId;
        }

        public Long getCourseId() {
            return courseId;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return courseId.equals(pk.courseId) && userId.equals(pk.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(courseId, userId);
        }
    }
}
