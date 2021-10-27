package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exams_examid_seq")
    @SequenceGenerator(name = "exams_examid_seq", sequenceName = "exams_examid_seq", allocationSize = 1)
    private Long examId;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable = false, updatable = false)
    private Course course;

    @Column
    private Time startTime;

    @Column
    private Time endTime;

    @Column
    private String title;

    @Column
    private String description;

    @OneToOne //TODO: check if this is correct
    @JoinColumn(name = "fileId", insertable = false, updatable = false)
    private FileModel examFile;

//    @OneToOne //TODO: check if this is correct
//    @JoinColumn(name = "fileId",nullable = true,insertable = false,updatable = false)
//    private FileModel answersFile = null; // Answers given by the teacher


    /* default */ Exam() {
        //For Hibernate
    }

    public static class Builder {
        private Long examId;
        private Course course;
        private Time startTime;
        private Time endTime;
        private String title;
        private String description;
        private FileModel examFile;
//        private FileModel answersFile;


        public Builder() {
        }

        public Builder(Long examId,Course course, Time startTime, Time endTime, String title, String description, FileModel examFile, FileModel answersFile) {
            this.examId = examId;
            this.course = course;
            this.startTime = startTime;
            this.endTime = endTime;
            this.title = title;
            this.description = description;
            this.examFile = examFile;
//            this.answersFile = answersFile;
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

//        public Builder withAnswerFile(FileModel file) {
//            this.answersFile = file;
//            return Builder.this;
//        }

        public Builder withStartTime(Time startTime) {
            this.startTime = startTime;
            return Builder.this;
        }

        public Builder withEndTime(Time endTime) {
            this.endTime = endTime;
            return Builder.this;
        }

        public Builder withCourse(Course course){
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

    private Exam(Builder builder){
        this.examId = builder.examId;
        this.examFile = builder.examFile;
//        this.answersFile = builder.answersFile;
        this.description = builder.description;
        this.course = builder.course;
        this.endTime = builder.endTime;
        this.startTime = builder.startTime;
        this.title = builder.title;
    }

    public Exam(Course course, Time startTime, Time endTime, String title, String description, FileModel examFile, FileModel answersFile) {
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.examFile = examFile;
//        this.answersFile = answersFile;
    }

    public void merge(Exam exam) {
        this.course = exam.course;
        this.startTime = exam.startTime;
        this.endTime = exam.endTime;
        this.title = exam.title;
        this.description = exam.description;
        this.examFile = exam.examFile;
//        this.answersFile = exam.answersFile;
    }
}
