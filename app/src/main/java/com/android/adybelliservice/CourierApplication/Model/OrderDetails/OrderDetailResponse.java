package com.android.adybelliservice.CourierApplication.Model.OrderDetails;

import java.util.ArrayList;

public class OrderDetailResponse {
    private Integer count;
    private ArrayList<OrderProduct> rows=new ArrayList<>();

    public OrderDetailResponse(Integer count, ArrayList<OrderProduct> rows) {
        this.count = count;
        this.rows = rows;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<OrderProduct> getRows() {
        return rows;
    }

    public void setRows(ArrayList<OrderProduct> rows) {
        this.rows = rows;
    }
}
