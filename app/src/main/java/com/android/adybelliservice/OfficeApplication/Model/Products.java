package com.android.adybelliservice.OfficeApplication.Model;

public class Products {
    private Integer id;
    private String name;
    private String image;
    private String brand;
    private String price;
    private String size;

    public Products(Integer id, String name, String image, String brand, String price, String size) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.brand = brand;
        this.price = price;
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
