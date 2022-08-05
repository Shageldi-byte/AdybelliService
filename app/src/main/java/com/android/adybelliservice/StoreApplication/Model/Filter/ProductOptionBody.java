package com.android.adybelliservice.StoreApplication.Model.Filter;



import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Brands;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Category;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Color;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Size;

import java.util.ArrayList;

public class ProductOptionBody {
    private ArrayList<Size> sizes=new ArrayList<>();
    private ArrayList<com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Brands> trademarks=new ArrayList<>();
    private ArrayList<com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Category> categories=new ArrayList<>();
    private ArrayList<com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Color> colors=new ArrayList<>();

    public ProductOptionBody(ArrayList<Size> sizes, ArrayList<Brands> trademarks, ArrayList<Category> categories, ArrayList<Color> colors) {
        this.sizes = sizes;
        this.trademarks = trademarks;
        this.categories = categories;
        this.colors = colors;
    }

    public ArrayList<Size> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<Size> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<Brands> getTrademarks() {
        return trademarks;
    }

    public void setTrademarks(ArrayList<Brands> trademarks) {
        this.trademarks = trademarks;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }
}
