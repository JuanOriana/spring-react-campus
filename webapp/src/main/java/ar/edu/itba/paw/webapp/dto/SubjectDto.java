package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Subject;

public class SubjectDto {

    private long subjectId;
    private String code;
    private String name;

    public static SubjectDto fromSubject(Subject subject){
        if (subject == null){
            return null;
        }

        final SubjectDto dto = new SubjectDto();
        dto.subjectId = subject.getSubjectId();
        dto.code = subject.getCode();
        dto.name = subject.getName();
        return dto;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
