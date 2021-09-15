package ar.edu.itba.paw.models;

public class User {

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private Integer fileNumber;
    private Integer userId;
    private boolean isAdmin;

    public static class Builder {

        private String name;
        private String surname;
        private String username;
        private String email;
        private String password;
        private Integer fileNumber;
        private Integer userId;
        private boolean isAdmin;

        public Builder() {
        }

        Builder(String name, String surname, String username, String email, String password, Integer fileNumber, Integer userId, boolean isAdmin) {
            this.name = name;
            this.surname = surname;
            this.username = username;
            this.email = email;
            this.password = password;
            this.fileNumber = fileNumber;
            this.userId = userId;
            this.isAdmin = isAdmin;
        }



        public Builder withName(String name){
            this.name = name;
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

        public Builder withUserId(Integer userId){
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
