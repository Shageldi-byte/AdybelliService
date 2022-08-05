package com.android.adybelliservice.OfficeApplication.Model;

public class InStockCourierModel {
    private Integer id;
    private String date;

    public InStockCourierModel(Integer id, String date) {
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
