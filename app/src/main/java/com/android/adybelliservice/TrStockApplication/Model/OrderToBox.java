package com.android.adybelliservice.TrStockApplication.Model;

public class OrderToBox {
    private String od_id;
    private String box_id;

    public OrderToBox(String od_id, String box_id) {
        this.od_id = od_id;
        this.box_id = box_id;
    }

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }
}
