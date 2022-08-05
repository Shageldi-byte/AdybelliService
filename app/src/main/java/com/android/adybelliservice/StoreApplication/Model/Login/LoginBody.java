package com.android.adybelliservice.StoreApplication.Model.Login;

public class LoginBody {
    private String name;
    private String surname;
    private String phone;
    private String user_id;
    private String role;
    private String gender;
    private String email;
    private String date_birth;
    private String token;

    public LoginBody(String name, String surname, String phone, String user_id, String role, String gender, String email, String date_birth, String token) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.user_id = user_id;
        this.role = role;
        this.gender = gender;
        this.email = email;
        this.date_birth = date_birth;
        this.token = token;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
