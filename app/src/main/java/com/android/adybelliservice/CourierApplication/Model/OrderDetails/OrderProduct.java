package com.android.adybelliservice.CourierApplication.Model.OrderDetails;

import java.util.ArrayList;

public class OrderProduct {
    private String od_id;
    private String order_id;
    private String size;
    private Integer count;
    private Double price;
    private String image;
    private String name;
    private String name_ru;
    private Integer tm_id;
    private String origin_url;
    private Double usd_price;
    private Double inc_percent;
    private Double cargo_price;
    private Double cost_price;
    private String status;
    private String order_id_tr;
    private String cat_list;
    private String prod_id;
    private Integer delivery_id;
    private OrderProductInfo product;
    private ArrayList<BoxToOds> boxToOds=new ArrayList<>();

    public OrderProduct(String od_id, String order_id, String size, Integer count, Double price, String image, String name, String name_ru, Integer tm_id, String origin_url, Double usd_price, Double inc_percent, Double cargo_price, Double cost_price, String status, String order_id_tr, String cat_list, String prod_id, Integer delivery_id, OrderProductInfo product, ArrayList<BoxToOds> boxToOds) {
        this.od_id = od_id;
        this.order_id = order_id;
        this.size = size;
        this.count = count;
        this.price = price;
        this.image = image;
        this.name = name;
        this.name_ru = name_ru;
        this.tm_id = tm_id;
        this.origin_url = origin_url;
        this.usd_price = usd_price;
        this.inc_percent = inc_percent;
        this.cargo_price = cargo_price;
        this.cost_price = cost_price;
        this.status = status;
        this.order_id_tr = order_id_tr;
        this.cat_list = cat_list;
        this.prod_id = prod_id;
        this.delivery_id = delivery_id;
        this.product = product;
        this.boxToOds = boxToOds;
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

    public Integer getTm_id() {
        return tm_id;
    }

    public void setTm_id(Integer tm_id) {
        this.tm_id = tm_id;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public Double getUsd_price() {
        return usd_price;
    }

    public void setUsd_price(Double usd_price) {
        this.usd_price = usd_price;
    }

    public Double getInc_percent() {
        return inc_percent;
    }

    public void setInc_percent(Double inc_percent) {
        this.inc_percent = inc_percent;
    }

    public Double getCargo_price() {
        return cargo_price;
    }

    public void setCargo_price(Double cargo_price) {
        this.cargo_price = cargo_price;
    }

    public Double getCost_price() {
        return cost_price;
    }

    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
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

    public String getCat_list() {
        return cat_list;
    }

    public void setCat_list(String cat_list) {
        this.cat_list = cat_list;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public Integer getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(Integer delivery_id) {
        this.delivery_id = delivery_id;
    }

    public OrderProductInfo getProduct() {
        return product;
    }

    public void setProduct(OrderProductInfo product) {
        this.product = product;
    }

    public ArrayList<BoxToOds> getBoxToOds() {
        return boxToOds;
    }

    public void setBoxToOds(ArrayList<BoxToOds> boxToOds) {
        this.boxToOds = boxToOds;
    }
}
