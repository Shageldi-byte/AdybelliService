package com.android.adybelliservice.StoreApplication.Model.Product;

public class GetProductBody {
    private Integer page;
    private Integer per_page;
    private String status;

    public GetProductBody(Integer page, Integer per_page, String status) {
        this.page = page;
        this.per_page = per_page;
        this.status = status;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
