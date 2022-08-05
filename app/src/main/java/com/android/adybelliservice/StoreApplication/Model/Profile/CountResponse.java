package com.android.adybelliservice.StoreApplication.Model.Profile;

public class CountResponse {
    private Integer accepted_products;
    private Integer pending_products;
    private Integer canceled_products;

    public CountResponse(Integer accepted_products, Integer pending_products, Integer canceled_products) {
        this.accepted_products = accepted_products;
        this.pending_products = pending_products;
        this.canceled_products = canceled_products;
    }

    public Integer getAccepted_products() {
        return accepted_products;
    }

    public void setAccepted_products(Integer accepted_products) {
        this.accepted_products = accepted_products;
    }

    public Integer getPending_products() {
        return pending_products;
    }

    public void setPending_products(Integer pending_products) {
        this.pending_products = pending_products;
    }

    public Integer getCanceled_products() {
        return canceled_products;
    }

    public void setCanceled_products(Integer canceled_products) {
        this.canceled_products = canceled_products;
    }
}
