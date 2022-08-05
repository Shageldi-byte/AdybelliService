package com.android.adybelliservice.StoreApplication.Model.Filter;

import java.util.ArrayList;

public class GetAddInfo {
    private ArrayList<Category> categories=new ArrayList<>();
    private ArrayList<Brands> brands=new ArrayList<>();
    private ArrayList<Color> colors=new ArrayList<>();

    public GetAddInfo(ArrayList<Category> categories, ArrayList<Brands> brands, ArrayList<Color> colors) {
        this.categories = categories;
        this.brands = brands;
        this.colors = colors;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Brands> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<Brands> brands) {
        this.brands = brands;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }
}
