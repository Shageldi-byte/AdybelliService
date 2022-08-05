package com.android.adybelliservice.CourierApplication.Model.Orders;

import com.android.adybelliservice.CourierApplication.Model.Box.StatusDetail;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderProduct;

import java.util.ArrayList;

public class OneOrderResponse {
    private String order_id;
    private String status;
    private String user_id;
    private Double total;
    private Double shipping_price;
    private String delivery_day;
    private Integer payment_method;
    private String address;
    private String created_at;
    private String updated_at;
    private String fullname;
    private String email;
    private String phone;
    private ArrayList<OrderProduct> products=new ArrayList<>();

    public OneOrderResponse(String order_id, String status, String user_id, Double total, Double shipping_price, String delivery_day, Integer payment_method, String address, String created_at, String updated_at, String fullname, String email, String phone, ArrayList<OrderProduct> products) {
        this.order_id = order_id;
        this.status = status;
        this.user_id = user_id;
        this.total = total;
        this.shipping_price = shipping_price;
        this.delivery_day = delivery_day;
        this.payment_method = payment_method;
        this.address = address;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.products = products;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(Double shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getDelivery_day() {
        return delivery_day;
    }

    public void setDelivery_day(String delivery_day) {
        this.delivery_day = delivery_day;
    }

    public Integer getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Integer payment_method) {
        this.payment_method = payment_method;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<OrderProduct> products) {
        this.products = products;
    }
}
