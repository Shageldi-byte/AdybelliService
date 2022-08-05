package com.android.adybelliservice.OfficeApplication.Model;

public class InStockModel {
    private Integer id;
    private String name;
    private String name_truck;
    private String status;
    private String created_at;
    private String updatedAt;
    private Integer prod_count;

    public InStockModel(Integer id, String name, String name_truck, String status, String created_at, String updatedAt, Integer prod_count) {
        this.id = id;
        this.name = name;
        this.name_truck = name_truck;
        this.status = status;
        this.created_at = created_at;
        this.updatedAt = updatedAt;
        this.prod_count = prod_count;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getProd_count() {
        return prod_count;
    }

    public void setProd_count(Integer prod_count) {
        this.prod_count = prod_count;
    }
}
