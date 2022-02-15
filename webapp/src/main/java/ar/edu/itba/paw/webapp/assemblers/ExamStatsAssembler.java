package ar.edu.itba.paw.webapp.assemblers;

import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.webapp.dtos.exam.ExamDto;
import ar.edu.itba.paw.webapp.dtos.exam.ExamStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExamStatsAssembler {

    @Autowired
    private ExamService examService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerAssembler answerAssembler;

    @Autowired
    private ExamAssembler examAssembler;

    public ExamStatsAssembler() {
        // For spring
    }

    public ExamStatsDto toResource(Exam entity, boolean showDeepLinks) {
        ExamDto examDto = examAssembler.toResource(entity, showDeepLinks);
        ExamStatsDto exam = new ExamStatsDto();
        List<Answer> corrected = answerService.getFilteredAnswers(entity.getExamId(), "corrected", 1, 50).getContent();
        List<Answer> notCorrected = answerService.getFilteredAnswers(entity.getExamId(), "not corrected", 1, 50).getContent();
        exam.setExam(examDto);
        exam.setCorrected(answerAssembler.toResources(corrected.isEmpty() ? Collections.emptyList() : corrected, showDeepLinks));
        exam.setNotCorrected(answerAssembler.toResources(notCorrected.isEmpty() ? Collections.emptyList() : notCorrected, showDeepLinks));
        exam.setAverage(examService.getAverageScoreOfExam(entity.getExamId()));
        return exam;
    }

    public List<ExamStatsDto> toResources(List<Exam> exams, boolean showDeepLinks) {
        List<ExamStatsDto> examStatsDtoList = new ArrayList<>();
        exams.forEach(e -> examStatsDtoList.add(toResource(e, showDeepLinks)));
        return examStatsDtoList;
    }
}
