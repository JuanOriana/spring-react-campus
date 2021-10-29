package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "answers", uniqueConstraints = {
       @UniqueConstraint(columnNames = {"deliveredDate", "studentId", "examId"})})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_answerid_seq")
    @SequenceGenerator(name = "answers_answerid_seq", sequenceName = "answers_answerid_seq", allocationSize = 1)
    private Long answerId;

    @ManyToOne
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

    public Answer(Exam exam, LocalDateTime deliveredDate, User student, User teacher, FileModel answerFile, Float score, String corrections) {
        this.exam = exam;
        this.deliveredDate = deliveredDate;
        this.student = student;
        this.teacher = teacher;
        this.answerFile = answerFile;
        this.score = score;
        this.corrections = corrections;
    }

    //TODO: IMPLEMENT BUILDER
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
        this.student = answer.student;
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
}
