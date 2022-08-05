package com.android.adybelliservice.TrStockApplication.Model;

public class ChangeOrderStatusOrdered {
    private String od_id;
    private String order_id_tr;

    public ChangeOrderStatusOrdered(String od_id, String order_id_tr) {
        this.od_id = od_id;
        this.order_id_tr = order_id_tr;
    }

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public String getOrder_id_tr() {
        return order_id_tr;
    }

    public void setOrder_id_tr(String order_id_tr) {
        this.order_id_tr = order_id_tr;
    }
}
