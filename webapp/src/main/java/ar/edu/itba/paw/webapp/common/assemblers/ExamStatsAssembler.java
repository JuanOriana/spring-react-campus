package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.webapp.controllers.ExamController;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import ar.edu.itba.paw.webapp.dto.ExamStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ExamStatsAssembler extends JaxRsResourceAssemblerSupport<Exam, ExamStatsDto> {

    @Autowired
    private ExamService examService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerAssembler answerAssembler;

    @Autowired
    private ExamAssembler examAssembler;

    public ExamStatsAssembler() {
        super(ExamController.class, ExamStatsDto.class);
    }

    @Override
    public ExamStatsDto toResource(Exam entity) {
        ExamDto examDto = examAssembler.toResource(entity);
        ExamStatsDto exam = new ExamStatsDto();
        List<Answer> corrected = answerService.getFilteredAnswers(entity.getExamId(), "corrected", 1, 50).getContent();
        List<Answer> notCorrected = answerService.getFilteredAnswers(entity.getExamId(), "not corrected", 1, 50).getContent();
        exam.setExam(examDto);
        exam.setCorrected(answerAssembler.toResources(corrected.isEmpty() ? Collections.emptyList() : corrected));
        exam.setNotCorrected(answerAssembler.toResources(notCorrected.isEmpty() ? Collections.emptyList() : notCorrected));
        exam.setAverage(examService.getAverageScoreOfExam(entity.getExamId()));
        return exam;
    }
}
