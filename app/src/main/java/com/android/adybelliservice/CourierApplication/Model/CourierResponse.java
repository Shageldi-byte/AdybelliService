package com.android.adybelliservice.CourierApplication.Model;

import java.util.ArrayList;

public class CourierResponse {
    private Integer count;
    private ArrayList<Courier> rows=new ArrayList<>();

    public CourierResponse(Integer count, ArrayList<Courier> rows) {
        this.count = count;
        this.rows = rows;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<Courier> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Courier> rows) {
        this.rows = rows;
    }
}
