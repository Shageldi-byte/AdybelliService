package com.android.adybelliservice.StoreApplication.Model.AddProduct;

import com.google.gson.annotations.SerializedName;

public class AdditionalInformation {
    private Integer id;
    @SerializedName("text")
    private String text;
    @SerializedName("text_ru")
    private String text_ru;

    public AdditionalInformation(Integer id, String text, String text_ru) {
        this.id = id;
        this.text = text;
        this.text_ru = text_ru;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText_ru() {
        return text_ru;
    }

    public void setText_ru(String text_ru) {
        this.text_ru = text_ru;
    }
}
