package com.android.adybelliservice.CourierApplication.Model.OrderDetails;

public class EditOrder {
    private String status;
    private String delivery_id;

    public EditOrder(String status, String delivery_id) {
        this.status = status;
        this.delivery_id = delivery_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }
}
