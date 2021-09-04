package ar.edu.itba.paw.models;

public class Subject {
    long subjectId;
    String code, name;

    public Subject(long subjectId, String code, String name) {
        this.subjectId = subjectId;
        this.code = code;
        this.name = name;
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
