package ar.edu.itba.paw.webapp.dtos.subject;

import ar.edu.itba.paw.models.Subject;
import org.springframework.hateoas.Link;

import java.io.Serializable;
import java.util.List;

public class SubjectDto implements Serializable {

    private long subjectId;
    private String code;
    private String name;
    private List<Link> links;

    public SubjectDto() {
        // For MessageBodyWriter
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

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
