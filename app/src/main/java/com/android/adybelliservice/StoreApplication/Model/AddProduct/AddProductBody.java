package com.android.adybelliservice.StoreApplication.Model.AddProduct;

import java.util.ArrayList;

public class AddProductBody {
    private String categories;
    private String name;
    private String name_ru;
    private Double price;
    private Double sale_price;
    private String slug;
    private String tm_id;
    private String color_id;
    private ArrayList<AdditionalInformation> descriptions=new ArrayList<>();
    private ArrayList<AddSize> sizes=new ArrayList<>();
    private ArrayList<ImageResponse> images=new ArrayList<>();

    public AddProductBody(String categories, String name, String name_ru, Double price, Double sale_price, String slug, String tm_id, String color_id, ArrayList<AdditionalInformation> descriptions, ArrayList<AddSize> sizes, ArrayList<ImageResponse> images) {
        this.categories = categories;
        this.name = name;
        this.name_ru = name_ru;
        this.price = price;
        this.sale_price = sale_price;
        this.slug = slug;
        this.tm_id = tm_id;
        this.color_id = color_id;
        this.descriptions = descriptions;
        this.sizes = sizes;
        this.images = images;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_ru() {
        return name_ru;
    }

    public void setName_ru(String name_ru) {
        this.name_ru = name_ru;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTm_id() {
        return tm_id;
    }

    public void setTm_id(String tm_id) {
        this.tm_id = tm_id;
    }

    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public ArrayList<AdditionalInformation> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<AdditionalInformation> descriptions) {
        this.descriptions = descriptions;
    }

    public ArrayList<AddSize> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<AddSize> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<ImageResponse> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageResponse> images) {
        this.images = images;
    }
}
