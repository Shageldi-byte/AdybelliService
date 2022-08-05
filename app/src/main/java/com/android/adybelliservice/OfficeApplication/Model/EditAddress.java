package com.android.adybelliservice.OfficeApplication.Model;

public class EditAddress {
    private String status;
    private String address;

    public EditAddress(String status, String address) {
        this.status = status;
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
