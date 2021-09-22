package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class CampusUser extends User {
    private Integer fileNumber;
    private Long userId;
    private String name, surname, email;
    private final boolean isAdmin;

    public CampusUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                      Integer fileNumber, Long userId, String name, String surname, String email,
                      boolean isAdmin) {
        super(username, password, authorities);
        this.fileNumber = fileNumber;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Integer getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(Integer fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
