package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
public class ExamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exams_examid_seq")
    @SequenceGenerator(name = "exams_examid_seq", sequenceName = "exams_examid_seq", allocationSize = 1)
    private Long examId;

    @Column
    private String title;

    @Column
    private String instructions;

    @OneToOne //TODO: check if this is correct
    @JoinColumn(name = "fileId")
    private FileModel file;

    @Column
    private LocalDateTime examStartDate;

    @Column
    private LocalDateTime examFinishDate;

    /* default */ ExamModel() {
        //For Hibernate
    }

    public static class Builder {
        private Long examId;
        private String title;
        private String instructions;
        private FileModel file;
        private LocalDateTime examStartDate;
        private LocalDateTime examFinishDate;

        public Builder() {
        }

        public Builder(Long examId, String title, String instructions, FileModel file, LocalDateTime examStartDate, LocalDateTime examFinishDate) {
            this.examId = examId;
            this.title = title;
            this.instructions = instructions;
            this.file = file;
            this.examStartDate = examStartDate;
            this.examFinishDate = examFinishDate;
        }

        public ExamModel.Builder withExamId(Long fileId) {
            this.examId = fileId;
            return ExamModel.Builder.this;
        }

        public ExamModel.Builder withTitle(String title) {
            this.title = title;
            return ExamModel.Builder.this;
        }

        public ExamModel.Builder withInstructions(String instructions) {
            this.instructions = instructions;
            return ExamModel.Builder.this;
        }

        public ExamModel.Builder withFile(FileModel file) {
            this.file = file;
            return ExamModel.Builder.this;
        }

        public ExamModel.Builder withStartDate(LocalDateTime startDate) {
            this.examStartDate = startDate;
            return ExamModel.Builder.this;
        }

        public ExamModel.Builder withFinishDate(LocalDateTime finishDate) {
            this.examFinishDate = finishDate;
            return ExamModel.Builder.this;
        }

        public ExamModel build() {
            if (this.title == null) {
                throw new NullPointerException("The property \"title\" is null. "
                        + "Please set the value by \"title()\". "
                        + "The properties \"title\", \"instructions\", \"file\", \"examStartDate\" and \"examFinishDate\" are required.");
            }
            if (this.instructions == null) {
                throw new NullPointerException("The property \"instructions\" is null. "
                        + "Please set the value by \"instructions()\". "
                        + "The properties \"title\", \"instructions\", \"file\", \"examStartDate\" and \"examFinishDate\" are required.");
            }
            if (this.file == null) {
                throw new NullPointerException("The property \"file\" is null. "
                        + "Please set the value by \"file()\". "
                        + "The properties \"title\", \"instructions\", \"file\", \"examStartDate\" and \"examFinishDate\" are required.");
            }
            if (this.examStartDate == null) {
                throw new NullPointerException("The property \"examStartDate\" is null. "
                        + "Please set the value by \"withStartDate()\". "
                        + "The properties \"title\", \"instructions\", \"file\", \"examStartDate\" and \"examFinishDate\" are required.");
            }
            if (this.examFinishDate == null) {
                throw new NullPointerException("The property \"examFinishDate\" is null. "
                        + "Please set the value by \"withFinishDate()\". "
                        + "The properties \"title\", \"instructions\", \"file\", \"examStartDate\" and \"examFinishDate\" are required.");
            }
            return new ExamModel(this);
        }
    }

    private ExamModel(Builder builder) {
        this.examId = builder.examId;
        this.title = builder.title;
        this.instructions = builder.instructions;
        this.file = builder.file;
        this.examStartDate = builder.examStartDate;
        this.examFinishDate = builder.examFinishDate;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public FileModel getFile() {
        return file;
    }

    public void setFile(FileModel file) {
        this.file = file;
    }

    public LocalDateTime getExamStartDate() {
        return examStartDate;
    }

    public void setExamStartDate(LocalDateTime examStartDate) {
        this.examStartDate = examStartDate;
    }

    public LocalDateTime getExamFinishDate() {
        return examFinishDate;
    }

    public void setExamFinishDate(LocalDateTime examFinishDate) {
        this.examFinishDate = examFinishDate;
    }

    public void merge(ExamModel exam) {
        this.title = this.title.equals(exam.getTitle()) ? this.title : exam.getTitle();
        this.instructions = this.instructions.equals(exam.getInstructions()) ? this.instructions : exam.getInstructions();
        this.file = this.file.equals(exam.getFile()) ? this.file : exam.getFile();
        this.examStartDate = this.examStartDate.equals(exam.getExamStartDate()) ? this.examStartDate : exam.getExamStartDate();
        this.examFinishDate = this.examFinishDate.equals(exam.getExamFinishDate()) ? this.examFinishDate : exam.getExamFinishDate();
    }
}
