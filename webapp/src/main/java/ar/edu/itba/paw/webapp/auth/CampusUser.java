package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

public class CampusUser extends User {
    private Integer fileNumber;
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private byte[] image;
    private boolean isAdmin;

    public CampusUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                      Integer fileNumber, Long userId, String name, String surname, String email,
                      boolean isAdmin, byte[] image) {
        super(username, password, authorities);
        this.fileNumber = fileNumber;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isAdmin = isAdmin;
        this.image = image;
    }

    public ar.edu.itba.paw.models.User toUser() {
        return new ar.edu.itba.paw.models.User.Builder()
                .withFileNumber(this.fileNumber)
                .withUserId(this.userId)
                .withName(this.name)
                .withSurname(this.surname)
                .withEmail(this.email)
                .withProfileImage(this.image)
                .isAdmin(this.isAdmin)
                .build();
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CampusUser user = (CampusUser) o;
        return Objects.equals(fileNumber, user.fileNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fileNumber);
    }
}
