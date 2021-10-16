package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@SecondaryTable(name = "profile_images",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "userId"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(name = "users_userid_seq", sequenceName = "users_userid_seq", allocationSize = 1)
    private Long userId;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private Integer fileNumber;

    @Column
    private boolean isAdmin;

    @Column(table = "profile_images")
    private byte[] image;

    /* Default */ User() {
        // Just for Hibernate
    }

    public User(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.fileNumber = user.getFileNumber();
        this.userId = user.getUserId();
        this.isAdmin = user.isAdmin();
        this.image = user.getImage();
    }

    public static class Builder {

        private String name;
        private String surname;
        private String username;
        private String email;
        private String password;
        private Integer fileNumber;
        private Long userId;
        private boolean isAdmin;
        private byte[] image;

        public Builder() {
        }

        Builder(String name, String surname, String username, String email, String password, Integer fileNumber,
                Long userId, boolean isAdmin, byte[] image) {
            this.name = name;
            this.surname = surname;
            this.username = username;
            this.email = email;
            this.password = password;
            this.fileNumber = fileNumber;
            this.userId = userId;
            this.isAdmin = isAdmin;
            this.image = image;
        }

        public Builder withName(String name){
            this.name = name;
            return Builder.this;
        }

        public Builder withProfileImage(byte[] image){
            this.image = image;
            return Builder.this;
        }

        public Builder withSurname(String surname){
            this.surname = surname;
            return Builder.this;
        }

        public Builder withUsername(String username){
            this.username = username;
            return Builder.this;
        }

        public Builder withEmail(String email){
            this.email = email;
            return Builder.this;
        }

        public Builder withPassword(String password){
            this.password = password;
            return Builder.this;
        }

        public Builder withFileNumber(Integer fileNumber){
            this.fileNumber = fileNumber;
            return Builder.this;
        }

        public Builder withUserId(Long userId){
            this.userId = userId;
            return Builder.this;
        }

        public Builder isAdmin(boolean isAdmin){
            this.isAdmin = isAdmin;
            return Builder.this;
        }

        public User build() {
            if(this.name == null){
                throw new NullPointerException("The property \"name\" is null. "
                        + "Please set the value by \"name()\". "
                        + "The properties \"name\", \"surname\", \"username\", \"email\", \"password\", \"fileNumber\" and \"userId\" are required.");
            }
            if(this.surname == null){
                throw new NullPointerException("The property \"surname\" is null. "
                        + "Please set the value by \"surname()\". "
                        + "The properties \"name\", \"surname\", \"username\", \"email\", \"password\", \"fileNumber\" and \"userId\" are required.");
            }
            if(this.username == null){
                throw new NullPointerException("The property \"username\" is null. "
                        + "Please set the value by \"username()\". "
                        + "The properties \"name\", \"surname\", \"username\", \"email\", \"password\", \"fileNumber\" and \"userId\" are required.");
            }
            if(this.email == null){
                throw new NullPointerException("The property \"email\" is null. "
                        + "Please set the value by \"email()\". "
                        + "The properties \"name\", \"surname\", \"username\", \"email\", \"password\", \"fileNumber\" and \"userId\" are required.");
            }
            if(this.fileNumber == null){
                throw new NullPointerException("The property \"fileNumber\" is null. "
                        + "Please set the value by \"fileNumber()\". "
                        + "The properties \"name\", \"surname\", \"username\", \"email\", \"password\", \"fileNumber\" and \"userId\" are required.");
            }
            if(this.userId == null){
                throw new NullPointerException("The property \"userId\" is null. "
                        + "Please set the value by \"userId()\". "
                        + "The properties \"name\", \"surname\", \"username\", \"email\", \"password\", \"fileNumber\" and \"userId\" are required.");
            }

            return new User(this);
        }
    }

    private User(Builder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.fileNumber = builder.fileNumber;
        this.userId = builder.userId;
        this.isAdmin = builder.isAdmin;
        this.image = builder.image;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
