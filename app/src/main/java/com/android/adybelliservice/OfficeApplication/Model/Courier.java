package com.android.adybelliservice.OfficeApplication.Model;

import com.google.gson.annotations.SerializedName;

public class Courier {
    @SerializedName("user_id")
    private Integer id;
    private String name;
    private String surname;
    private String role;
    private String phone;

    public Courier(Integer id, String name, String surname, String role, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
