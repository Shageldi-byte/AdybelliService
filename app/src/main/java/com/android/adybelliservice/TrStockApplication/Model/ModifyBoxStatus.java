package com.android.adybelliservice.TrStockApplication.Model;

public class ModifyBoxStatus {
    private String box_id;
    private String status;

    public ModifyBoxStatus(String box_id, String status) {
        this.box_id = box_id;
        this.status = status;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
