package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.constraint.annotation.MaxFileSize;
import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SolveExamForm {

    @NotEmptyFile
    // 50mb
    @MaxFileSize(50)
    private CommonsMultipartFile exam;

    public CommonsMultipartFile getExam() {
        return exam;
    }

    public void setExam(CommonsMultipartFile exam) {
        this.exam = exam;
    }
}
