package com.android.adybelliservice.CourierApplication.Model;

public class Status {
    private String name;
    private String status;
    private Integer id;

    public Status(String name, String status, Integer id) {
        this.name = name;
        this.status = status;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
