package com.android.adybelliservice.StoreApplication.Model.Filter;

public class Color {
    private String color;
    private String color_ru;
    private String color_hex;
    private String pc_id;

    public Color(String color, String color_ru, String color_hex, String pc_id) {
        this.color = color;
        this.color_ru = color_ru;
        this.color_hex = color_hex;
        this.pc_id = pc_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor_ru() {
        return color_ru;
    }

    public void setColor_ru(String color_ru) {
        this.color_ru = color_ru;
    }

    public String getColor_hex() {
        return color_hex;
    }

    public void setColor_hex(String color_hex) {
        this.color_hex = color_hex;
    }

    public String getPc_id() {
        return pc_id;
    }

    public void setPc_id(String pc_id) {
        this.pc_id = pc_id;
    }
}
