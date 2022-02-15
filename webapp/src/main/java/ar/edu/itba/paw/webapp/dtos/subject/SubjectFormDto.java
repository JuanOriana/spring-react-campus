package ar.edu.itba.paw.webapp.dtos.subject;

import javax.validation.constraints.Size;

public class SubjectFormDto {

    @Size(min=1,max=50)
    private String code;

    @Size(min=2,max=50)
    private String name;


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
