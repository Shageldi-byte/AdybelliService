package com.android.adybelliservice.OfficeApplication.Model.Auth;

public class LoginResponse {
    private String user_id;
    private String name;
    private String surname;
    private String token;
    private String email;

    public LoginResponse(String user_id, String name, String surname, String token, String email) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.token = token;
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
