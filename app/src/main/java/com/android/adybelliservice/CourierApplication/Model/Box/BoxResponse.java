package com.android.adybelliservice.CourierApplication.Model.Box;


import com.android.adybelliservice.CourierApplication.Model.InStockModel;

import java.util.ArrayList;

public class BoxResponse {
    private Integer count;
    private ArrayList<InStockModel> rows=new ArrayList<>();

    public BoxResponse(Integer count, ArrayList<InStockModel> rows) {
        this.count = count;
        this.rows = rows;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<InStockModel> getRows() {
        return rows;
    }

    public void setRows(ArrayList<InStockModel> rows) {
        this.rows = rows;
    }
}
