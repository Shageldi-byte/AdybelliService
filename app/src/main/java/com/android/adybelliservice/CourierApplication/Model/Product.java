package com.android.adybelliservice.CourierApplication.Model;

public class Product {
    private Integer id;
    private String name;
    private Integer count;
    private Double price;
    private String image;

    public Product(Integer id, String name, Integer count, Double price, String image) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.image = image;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
