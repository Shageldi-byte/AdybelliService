package com.android.adybelliservice.StoreApplication.Model.Filter;

public class Brands {
    private String tm_id;
    private String title;

    public Brands(String tm_id, String title) {
        this.tm_id = tm_id;
        this.title = title;
    }

    public String getTm_id() {
        return tm_id;
    }

    public void setTm_id(String tm_id) {
        this.tm_id = tm_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
