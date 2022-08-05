package com.android.adybelliservice.CourierApplication.Model;


import com.android.adybelliservice.CourierApplication.Model.Box.StatusDetail;

public class InStockModel {
    private Integer id;
    private String name;
    private String name_truck;
    private String status;
    private String createdAt;
    private String updatedAt;
    private StatusDetail statusDetail;

    public InStockModel(Integer id, String name, String name_truck, String status, String createdAt, String updatedAt, StatusDetail statusDetail) {
        this.id = id;
        this.name = name;
        this.name_truck = name_truck;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statusDetail = statusDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public StatusDetail getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(StatusDetail statusDetail) {
        this.statusDetail = statusDetail;
    }
}
