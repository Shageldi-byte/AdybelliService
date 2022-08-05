package com.android.adybelliservice.StoreApplication.Model.AddProduct;

public class DefaultItem {
    private Integer id;
    private String title;
    private String title_ru;
    private String type;

    public DefaultItem(Integer id, String title, String title_ru, String type) {
        this.id = id;
        this.title = title;
        this.title_ru = title_ru;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_ru() {
        return title_ru;
    }

    public void setTitle_ru(String title_ru) {
        this.title_ru = title_ru;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
