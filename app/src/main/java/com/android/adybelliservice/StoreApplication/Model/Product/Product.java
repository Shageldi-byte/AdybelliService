package com.android.adybelliservice.StoreApplication.Model.Product;

import com.google.gson.annotations.SerializedName;

public class Product {
    private Integer prod_id;
    private String name;
    private String name_ru;
    private Double price;
    private Double sale_price;
    private String image;
    private Integer gender;
    private String trademark;
    private String color_hex;
    private String color_tm;
    private String color_ru;
    private String layout_type;
    private Boolean is_active;
    private Boolean on_sale;


    public Product(Integer prod_id, String name, String name_ru, Double price, Double sale_price, String image, Integer gender, String trademark, String color_hex, String color_tm, String color_ru, String layout_type, Boolean is_active, Boolean on_sale) {
        this.prod_id = prod_id;
        this.name = name;
        this.name_ru = name_ru;
        this.price = price;
        this.sale_price = sale_price;
        this.image = image;
        this.gender = gender;
        this.trademark = trademark;
        this.color_hex = color_hex;
        this.color_tm = color_tm;
        this.color_ru = color_ru;
        this.layout_type = layout_type;
        this.is_active = is_active;
        this.on_sale = on_sale;
    }

    public Integer getProd_id() {
        return prod_id;
    }

    public void setProd_id(Integer prod_id) {
        this.prod_id = prod_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getColor_hex() {
        return color_hex;
    }

    public void setColor_hex(String color_hex) {
        this.color_hex = color_hex;
    }

    public String getColor_tm() {
        return color_tm;
    }

    public void setColor_tm(String color_tm) {
        this.color_tm = color_tm;
    }

    public String getColor_ru() {
        return color_ru;
    }

    public void setColor_ru(String color_ru) {
        this.color_ru = color_ru;
    }

    public String getLayout_type() {
        return layout_type;
    }

    public void setLayout_type(String layout_type) {
        this.layout_type = layout_type;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Boolean getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(Boolean on_sale) {
        this.on_sale = on_sale;
    }
}
