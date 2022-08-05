package com.android.adybelliservice.StoreApplication.Model.AddProduct;

import android.net.Uri;

public class SelectedImage {
    private Uri uri;
    private Integer type;
    private String status;

    public SelectedImage(Uri uri, Integer type, String status) {
        this.uri = uri;
        this.type = type;
        this.status = status;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
