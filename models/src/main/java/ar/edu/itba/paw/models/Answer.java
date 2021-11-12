package ar.edu.itba.paw.models;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "answers", uniqueConstraints = {
       @UniqueConstraint(columnNames =  {"deliveredDate", "studentId", "examId" })})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_answerid_seq")
    @SequenceGenerator(name = "answers_answerid_seq", sequenceName = "answers_answerid_seq", allocationSize = 1)
    private Long answerId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "examId")
    private Exam exam;

    @Column(name = "deliveredDate")
    private LocalDateTime deliveredDate;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacherId")
    private User teacher;

    @OneToOne
    @JoinColumn
    private FileModel answerFile;

    @Column
    private Float score;

    @Column
    private String corrections;

    /* Default */ Answer() {
        // Just for Hibernate
    }

    public Answer(Exam exam, LocalDateTime deliveredDate, User student, User teacher, FileModel answerFile, Float score, String corrections) {
        this.exam = exam;
        this.deliveredDate = deliveredDate;
        this.student = student;
        this.teacher = teacher;
        this.answerFile = answerFile;
        this.score = score;
        this.corrections = corrections;
    }

    public Long getAnswerId() {
        return answerId;
    }
    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public FileModel getAnswerFile() {
        return answerFile;
    }

    public void setAnswerFile(FileModel answerFile) {
        this.answerFile = answerFile;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getCorrections() {
        return corrections;
    }

    public void setCorrections(String corrections) {
        this.corrections = corrections;
    }

    public void merge(Answer answer){
        this.answerFile = answer.answerFile;
        this.corrections = answer.corrections;
        this.deliveredDate = answer.deliveredDate;
        this.score =answer.score;
        this.exam = answer.exam;
        this.teacher = answer.teacher;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answers = (Answer) o;
        return answerId.equals(answers.answerId) && exam.equals(answers.exam) && student.equals(answers.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, exam, student);
    }

    public static class Builder {
        private Long answerId;
        private Exam exam;
        private LocalDateTime deliveredDate;
        private User student;
        private User teacher;
        private FileModel answerFile;
        private Float score;
        private String corrections;


        public Builder(){

        }
        public Builder(Long answerId, Exam exam, LocalDateTime deliveredDate, User student, User teacher, FileModel answerFile, Float score, String corrections) {
            this.answerId = answerId;
            this.exam = exam;
            this.deliveredDate = deliveredDate;
            this.student = student;
            this.teacher = teacher;
            this.answerFile = answerFile;
            this.score = score;
            this.corrections = corrections;
        }

        public Builder withAnswerId(Long answerId){
            this.answerId = answerId;
            return this;
        }
        public Builder withExam(Exam exam){
            this.exam = exam;
            return this;
        }
        public Builder withDeliveredDate(LocalDateTime time){
            this.deliveredDate = time;
            return this;
        }
        public Builder withStudent(User student){
            this.student = student;
            return this;
        }
        public Builder withTeacher(User teacher){
            this.teacher = teacher;
            return this;
        }
        public Builder withAnswerFile(FileModel file){
            this.answerFile = file;
            return this;
        }
        public Builder withScore(Float score){
            this.score = score;
            return this;
        }
        public Builder withCorrections(String corrections){
            this.corrections = corrections;
            return this;
        }

        public Answer build(){
            if (this.answerId == null) {
                throw new NullPointerException("The property \"answerId\" is null. "
                        + "Please set the value by \"answerId()\". "
                        + "The properties \"answerId\", \"exam\", \"student\"and\"deliveredDate\" are required.");
            }
            if (this.exam == null) {
                throw new NullPointerException("The property \"exam\" is null. "
                        + "Please set the value by \"exam()\". "
                        + "The properties \"answerId\", \"exam\", \"student\"and\"deliveredDate\" are required.");
            }
            if (this.student == null) {
                throw new NullPointerException("The property \"student\" is null. "
                        + "Please set the value by \"student()\". "
                        + "The properties \"answerId\", \"examId\", \"student\"and\"deliveredDate\" are required.");
            }
            if (this.deliveredDate == null) {
                throw new NullPointerException("The property \"delivereddate\" is null. "
                        + "Please set the value by \"deliveredDate()\". "
                        + "The properties \"answerId\", \"examId\", \"student\"and\"deliveredDate\" are required.");
            }
            return new Answer(this);
        }
    }

    private Answer(Builder builder){
        this.answerId = builder.answerId;
        this.exam = builder.exam;
        this.student = builder.student;
        this.deliveredDate = builder.deliveredDate;
        this.teacher = builder.teacher;
        this.score = builder.score;
        this.corrections = builder.corrections;
        this.answerFile = builder.answerFile;
    }
}
