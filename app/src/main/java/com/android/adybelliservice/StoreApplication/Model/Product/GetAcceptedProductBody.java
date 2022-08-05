package com.android.adybelliservice.StoreApplication.Model.Product;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GetAcceptedProductBody {
    @Nullable
    private String trademarks;
    @Nullable
    private String categories;
    @Nullable
    private String sizes;
    @Nullable
    private String colors;
    @Nullable
    private Integer is_discount;
    @Nullable
    private Integer page;
    @Nullable
    private Integer per_page;
    @Nullable
    private Integer sort;
    @Nullable
    private Double min;
    @Nullable
    private Double max;
    @Nullable
    private String seller_id;

    public GetAcceptedProductBody(@Nullable String trademarks, @Nullable String categories, @Nullable String sizes, @Nullable String colors, @Nullable Integer is_discount, @Nullable Integer page, @Nullable Integer per_page, @Nullable Integer sort, @Nullable Double min, @Nullable Double max, @Nullable String seller_id) {
        this.trademarks = trademarks;
        this.categories = categories;
        this.sizes = sizes;
        this.colors = colors;
        this.is_discount = is_discount;
        this.page = page;
        this.per_page = per_page;
        this.sort = sort;
        this.min = min;
        this.max = max;
        this.seller_id = seller_id;
    }

    @Nullable
    public String getTrademarks() {
        return trademarks;
    }

    public void setTrademarks(@Nullable String trademarks) {
        this.trademarks = trademarks;
    }

    @Nullable
    public String getCategories() {
        return categories;
    }

    public void setCategories(@Nullable String categories) {
        this.categories = categories;
    }

    @Nullable
    public String getSizes() {
        return sizes;
    }

    public void setSizes(@Nullable String sizes) {
        this.sizes = sizes;
    }

    @Nullable
    public String getColors() {
        return colors;
    }

    public void setColors(@Nullable String colors) {
        this.colors = colors;
    }

    @Nullable
    public Integer getIs_discount() {
        return is_discount;
    }

    public void setIs_discount(@Nullable Integer is_discount) {
        this.is_discount = is_discount;
    }

    @Nullable
    public Integer getPage() {
        return page;
    }

    public void setPage(@Nullable Integer page) {
        this.page = page;
    }

    @Nullable
    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(@Nullable Integer per_page) {
        this.per_page = per_page;
    }

    @Nullable
    public Integer getSort() {
        return sort;
    }

    public void setSort(@Nullable Integer sort) {
        this.sort = sort;
    }

    @Nullable
    public Double getMin() {
        return min;
    }

    public void setMin(@Nullable Double min) {
        this.min = min;
    }

    @Nullable
    public Double getMax() {
        return max;
    }

    public void setMax(@Nullable Double max) {
        this.max = max;
    }

    @Nullable
    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(@Nullable String seller_id) {
        this.seller_id = seller_id;
    }
}
