package com.android.adybelliservice.StoreApplication.Model.Orders;

public class GetUserOrderProducts {
    private String image;

    public GetUserOrderProducts(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
