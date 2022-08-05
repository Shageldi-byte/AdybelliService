package com.android.adybelliservice.TrStockApplication.Model;

public class ChangeProductBox {
    private String od_id;
    private String old_box_id;
    private String new_box_id;

    public ChangeProductBox(String od_id, String old_box_id, String new_box_id) {
        this.od_id = od_id;
        this.old_box_id = old_box_id;
        this.new_box_id = new_box_id;
    }

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public String getOld_box_id() {
        return old_box_id;
    }

    public void setOld_box_id(String old_box_id) {
        this.old_box_id = old_box_id;
    }

    public String getNew_box_id() {
        return new_box_id;
    }

    public void setNew_box_id(String new_box_id) {
        this.new_box_id = new_box_id;
    }
}
