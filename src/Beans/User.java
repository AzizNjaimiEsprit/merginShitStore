package Beans;

import java.util.Objects;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String telephone;
    private String login;
    private String password;
    private int role = 0;
    private String verificationCode;


    public User(int id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public User(int id, String fullName, String email, String telephone, String login, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
        this.login = login;
        this.password = password;
    }

    public User(String fullName, String email, String telephone, String login, String password, int role) {
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(int id, String fullName, String email, String telephone, String login, String password, int role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(int id) {
        this.id = id;
    }

    public User() {
    }

    public User(String fullName, String email, String telephone, String login, String password) {
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                role == user.role &&

                Objects.equals(fullName, user.fullName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(telephone, user.telephone) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(verificationCode, user.verificationCode);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", verificationCode='" + verificationCode + '\'' +

                '}';
    }
}
