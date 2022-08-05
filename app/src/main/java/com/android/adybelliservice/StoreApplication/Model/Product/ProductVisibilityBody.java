package com.android.adybelliservice.StoreApplication.Model.Product;

public class ProductVisibilityBody {
    private String prod_id;
    private String is_active;

    public ProductVisibilityBody(String prod_id, String is_active) {
        this.prod_id = prod_id;
        this.is_active = is_active;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
