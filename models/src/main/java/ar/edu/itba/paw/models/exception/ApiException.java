package ar.edu.itba.paw.models.exception;

import java.util.ArrayList;
import java.util.List;

public abstract class ApiException extends Exception{
    protected String status;
    protected String body;
    protected List<String> details = new ArrayList<>();
    protected int statusCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getDetails(){
        return details;
    }

    public void addDetails(String detail){
        details.add(detail);
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
