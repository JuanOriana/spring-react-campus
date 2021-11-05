package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exams_examid_seq")
    @SequenceGenerator(name = "exams_examid_seq", sequenceName = "exams_examid_seq", allocationSize = 1)
    private Long examId;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private String title;

    @Column
    private String description;

    @OneToOne
    private FileModel examFile;


    /* default */ Exam() {
        // For Hibernate
    }

    public static class Builder {
        private Long examId;
        private Course course;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String title;
        private String description;
        private FileModel examFile;


        public Builder() {
        }

        public Builder(Long examId, Course course, LocalDateTime startTime, LocalDateTime endTime, String title, String description, FileModel examFile) {
            this.examId = examId;
            this.course = course;
            this.startTime = startTime;
            this.endTime = endTime;
            this.title = title;
            this.description = description;
            this.examFile = examFile;
        }

        public Builder withExamId(Long fileId) {
            this.examId = fileId;
            return Builder.this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return Builder.this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return Builder.this;
        }

        public Builder withExamFile(FileModel file) {
            this.examFile = file;
            return Builder.this;
        }


        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return Builder.this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return Builder.this;
        }

        public Builder withCourse(Course course) {
            this.course = course;
            return Builder.this;
        }

        public Exam build() {
            if (this.title == null) {
                throw new NullPointerException("The property \"title\" is null. "
                        + "Please set the value by \"title()\". "
                        + "The properties \"title\", \"description\", \"examFile\", \"startTime\" and \"endTime\" are required.");
            }
            if (this.description == null) {
                throw new NullPointerException("The property \"description\" is null. "
                        + "Please set the value by \"instructions()\". "
                        + "The properties \"title\", \"description\", \"examFile\", \"startTime\" and \"endTime\" are required.");
            }
            if (this.examFile == null) {
                throw new NullPointerException("The property \"examFile\" is null. "
                        + "Please set the value by \"file()\". "
                        + "The properties \"title\", \"description\", \"examFile\", \"startTime\" and \"endTime\" are required.");
            }
            if (this.startTime == null) {
                throw new NullPointerException("The property \"startTime\" is null. "
                        + "Please set the value by \"withStartDate()\". "
                        + "The properties \"title\", \"description\", \"examFile\", \"startTime\" and \"endTime\" are required.");
            }
            if (this.endTime == null) {
                throw new NullPointerException("The property \"endTime\" is null. "
                        + "Please set the value by \"withFinishDate()\". "
                        + "The properties \"title\", \"description\", \"examFile\", \"startTime\" and \"endTime\" are required.");
            }
            return new Exam(this);
        }
    }

    private Exam(Builder builder) {
        this.examId = builder.examId;
        this.examFile = builder.examFile;
        this.description = builder.description;
        this.course = builder.course;
        this.endTime = builder.endTime;
        this.startTime = builder.startTime;
        this.title = builder.title;
    }

    public Exam(Course course, LocalDateTime startTime, LocalDateTime endTime, String title, String description, FileModel examFile) {
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.examFile = examFile;
    }

    public void merge(Exam exam) {
        this.course = exam.course;
        this.startTime = exam.startTime;
        this.endTime = exam.endTime;
        this.title = exam.title;
        this.description = exam.description;
        this.examFile = exam.examFile;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FileModel getExamFile() {
        return examFile;
    }

    public void setExamFile(FileModel examFile) {
        this.examFile = examFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return examId.equals(exam.examId) && course.equals(exam.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, course);
    }
}
