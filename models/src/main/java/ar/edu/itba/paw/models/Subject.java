package ar.edu.itba.paw.models;

public class Subject {
    Integer subjectId;
    String code, name;

    public Subject(Integer subjectId, String code, String name) {
        this.subjectId = subjectId;
        this.code = code;
        this.name = name;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
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
