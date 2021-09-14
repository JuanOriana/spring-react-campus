package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CampusUser extends User {
    private Integer fileNumber, userId;
    private String name, surname, email;

    public CampusUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer fileNumber, Integer userId, String name, String surname, String email) {
        super(username, password, authorities);
        this.fileNumber = fileNumber;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Integer getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(Integer fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
