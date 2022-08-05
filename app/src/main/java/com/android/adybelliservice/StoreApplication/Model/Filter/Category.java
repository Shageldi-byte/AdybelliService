package com.android.adybelliservice.StoreApplication.Model.Filter;

import java.util.ArrayList;

public class Category {

    private String cat_id;
    private String title;
    private String title_ru;
    private String parent_id;
    private ArrayList<Category> sub_categories=new ArrayList<>();

    public Category(String cat_id, String title, String title_ru, String parent_id, ArrayList<Category> sub_categories) {
        this.cat_id = cat_id;
        this.title = title;
        this.title_ru = title_ru;
        this.parent_id = parent_id;
        this.sub_categories = sub_categories;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public ArrayList<Category> getSub_categories() {
        return sub_categories;
    }

    public void setSub_categories(ArrayList<Category> sub_categories) {
        this.sub_categories = sub_categories;
    }
}
