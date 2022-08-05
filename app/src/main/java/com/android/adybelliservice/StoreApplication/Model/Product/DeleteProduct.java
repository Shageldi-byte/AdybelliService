package com.android.adybelliservice.StoreApplication.Model.Product;

public class DeleteProduct {
    private String pp_id;

    public DeleteProduct(String pp_id) {
        this.pp_id = pp_id;
    }

    public String getPp_id() {
        return pp_id;
    }

    public void setPp_id(String pp_id) {
        this.pp_id = pp_id;
    }
}
