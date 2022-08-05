package com.android.adybelliservice.OfficeApplication.Model.OrderDetails;

import androidx.annotation.Nullable;

public class EditOrder {
    private String od_id;
    private String status;
    @Nullable
    private String delivery_id;

    public EditOrder(String od_id, String status, @Nullable String delivery_id) {
        this.od_id = od_id;
        this.status = status;
        this.delivery_id = delivery_id;
    }

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Nullable
    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(@Nullable String delivery_id) {
        this.delivery_id = delivery_id;
    }
}
