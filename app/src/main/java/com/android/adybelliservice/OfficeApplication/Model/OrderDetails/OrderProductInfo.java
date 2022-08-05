package com.android.adybelliservice.OfficeApplication.Model.OrderDetails;

public class OrderProductInfo {
    private String color_id;
    private OrderProductColor productColor;

    public OrderProductInfo(String color_id, OrderProductColor productColor) {
        this.color_id = color_id;
        this.productColor = productColor;
    }

    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public OrderProductColor getProductColor() {
        return productColor;
    }

    public void setProductColor(OrderProductColor productColor) {
        this.productColor = productColor;
    }
}
