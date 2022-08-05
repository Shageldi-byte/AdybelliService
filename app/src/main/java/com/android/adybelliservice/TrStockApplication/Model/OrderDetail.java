package com.android.adybelliservice.TrStockApplication.Model;

public class OrderDetail {
    private String od_id;
    private String order_id;
    private String size;
    private Integer count;
    private Double price;
    private String image;
    private String name;
    private String name_ru;
    private String origin_url;
    private Double cost_price;
    private String prod_id;
    private String status;
    private String order_id_tr;
    private String trademark;
    private Integer delivery_id;

    public OrderDetail(String od_id, String order_id, String size, Integer count, Double price, String image, String name, String name_ru, String origin_url, Double cost_price, String prod_id, String status, String order_id_tr, String trademark, Integer delivery_id) {
        this.od_id = od_id;
        this.order_id = order_id;
        this.size = size;
        this.count = count;
        this.price = price;
        this.image = image;
        this.name = name;
        this.name_ru = name_ru;
        this.origin_url = origin_url;
        this.cost_price = cost_price;
        this.prod_id = prod_id;
        this.status = status;
        this.order_id_tr = order_id_tr;
        this.trademark = trademark;
        this.delivery_id = delivery_id;
    }

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public Double getCost_price() {
        return cost_price;
    }

    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_id_tr() {
        return order_id_tr;
    }

    public void setOrder_id_tr(String order_id_tr) {
        this.order_id_tr = order_id_tr;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public Integer getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(Integer delivery_id) {
        this.delivery_id = delivery_id;
    }
}
