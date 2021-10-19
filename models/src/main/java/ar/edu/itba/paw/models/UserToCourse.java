package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_to_course")
public class UserToCourse {

    @EmbeddedId
    private UserToCourseId userToCourseId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(targetEntity=Course.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId")
    private Course course;

    @OneToOne
    @JoinColumn(name = "roleId")
    private Role role;

    /*Default */ UserToCourse() {
        //Just for hibernate
    }

    public UserToCourse(User user, Course course, Role role) {
        this.user = user;
        this.course = course;
        this.role = role;
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

    private static class UserToCourseId implements Serializable {
        private Course course;
        private User user;


        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
