package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_subjectid_seq")
    @SequenceGenerator(name="subjects_subjectid_seq", sequenceName = "subjects_subjectid_seq", allocationSize = 1)
    private Long subjectId;

    @Column
    private String code;

    @Column(name = "subjectname")
    private String subjectname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", orphanRemoval = false)
    private List<Course> coursesList;

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    /* Default */ Subject() {
        // Just for Hibernate
    }
    public Subject(Long subjectId, String code, String name) {
        this.subjectId = subjectId;
        this.code = code;
        this.subjectname = name;
    }
    public Subject(String code, String name) {
        this.code = code;
        this.subjectname = name;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return subjectname;
    }

    public void setName(String name) {
        this.subjectname = name;
    }

    public void merge(Subject subject){
        this.code = subject.getCode();
        this.subjectname = subject.getName();
    }
}
