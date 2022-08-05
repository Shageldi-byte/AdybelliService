package com.android.adybelliservice.StoreApplication.Model.AddProduct;

public class AddResponse {
    private String prod_id;

    public AddResponse(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }
}
