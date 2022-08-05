package com.android.adybelliservice.StoreApplication.Model.Filter;

public class MainItem {
    private Integer id;
    private String text_tm;
    private String text_ru;
    private String type;

    public MainItem(Integer id, String text_tm, String text_ru, String type) {
        this.id = id;
        this.text_tm = text_tm;
        this.text_ru = text_ru;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText_tm() {
        return text_tm;
    }

    public void setText_tm(String text_tm) {
        this.text_tm = text_tm;
    }

    public String getText_ru() {
        return text_ru;
    }

    public void setText_ru(String text_ru) {
        this.text_ru = text_ru;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
