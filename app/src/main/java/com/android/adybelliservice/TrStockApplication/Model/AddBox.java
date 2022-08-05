package com.android.adybelliservice.TrStockApplication.Model;

public class AddBox {
    private String name;
    private String name_truck;

    public AddBox(String name, String name_truck) {
        this.name = name;
        this.name_truck = name_truck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_truck() {
        return name_truck;
    }

    public void setName_truck(String name_truck) {
        this.name_truck = name_truck;
    }
}
