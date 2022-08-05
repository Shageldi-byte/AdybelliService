package com.android.adybelliservice.StoreApplication.Model.AddProduct;

import com.google.gson.annotations.SerializedName;

public class AddSize {
    @SerializedName("label")
    private String label;
    @SerializedName("stockQuantity")
    private Integer stockQuantity;
    @SerializedName("barcode")
    private String barcode;

    public AddSize(String label, Integer stockQuantity, String barcode) {
        this.label = label;
        this.stockQuantity = stockQuantity;
        this.barcode = barcode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
