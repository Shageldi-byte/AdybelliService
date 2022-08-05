package com.android.adybelliservice.TrStockApplication.Model;

public class SetTruckName {
    private String box_id;
    private String name_truck;

    public SetTruckName(String box_id, String name_truck) {
        this.box_id = box_id;
        this.name_truck = name_truck;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getName_truck() {
        return name_truck;
    }

    public void setName_truck(String name_truck) {
        this.name_truck = name_truck;
    }
}
