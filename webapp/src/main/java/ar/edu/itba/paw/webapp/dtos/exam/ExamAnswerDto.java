package ar.edu.itba.paw.webapp.dtos.exam;

import ar.edu.itba.paw.webapp.dtos.answer.AnswerDto;

import java.io.Serializable;

public class ExamAnswerDto implements Serializable {

    ExamDto exam;
    AnswerDto answer;

    public ExamAnswerDto() {
        // For MessageBodyWriter
    }

    public ExamAnswerDto(ExamDto exam, AnswerDto answer) {
        this.exam = exam;
        this.answer = answer;
    }

    public ExamDto getExam() {
        return exam;
    }

    public void setExam(ExamDto exam) {
        this.exam = exam;
    }

    public AnswerDto getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDto answer) {
        this.answer = answer;
    }
}
