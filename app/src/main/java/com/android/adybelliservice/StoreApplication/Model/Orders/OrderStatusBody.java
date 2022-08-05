package com.android.adybelliservice.StoreApplication.Model.Orders;

public class OrderStatusBody {
    private String od_id;
    private String status;

    public OrderStatusBody(String od_id, String status) {
        this.od_id = od_id;
        this.status = status;
    }

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
