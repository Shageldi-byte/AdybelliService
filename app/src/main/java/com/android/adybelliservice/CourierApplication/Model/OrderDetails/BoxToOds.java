package com.android.adybelliservice.CourierApplication.Model.OrderDetails;

public class BoxToOds {
    private Integer id;
    private Integer box_id;
    private Integer od_id;

    public BoxToOds(Integer id, Integer box_id, Integer od_id) {
        this.id = id;
        this.box_id = box_id;
        this.od_id = od_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBox_id() {
        return box_id;
    }

    public void setBox_id(Integer box_id) {
        this.box_id = box_id;
    }

    public Integer getOd_id() {
        return od_id;
    }

    public void setOd_id(Integer od_id) {
        this.od_id = od_id;
    }
}
