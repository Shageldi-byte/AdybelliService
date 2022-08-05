package com.android.adybelliservice.StoreApplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.android.adybelliservice.StoreApplication.Model.Filter.MainItem;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.BrandFilter;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Category;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Colour;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Cost;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.DefaultFilter;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.FilterList;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Razmer;
import com.android.adybelliservice.StoreApplication.Fragment.Product.Products;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOptionBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreActivityFilterBinding;
//import com.android.adybelliservice.databinding.StoreActivityFilterBinding;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {
    private Context context = this;
    private ArrayList<MainItem> filters = new ArrayList<>();
    private StoreActivityFilterBinding binding;
    public static ProductOptionBody productOptionBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.store_anim_slide_in_left,
                R.anim.store_anim_slide_out_left);
        binding = StoreActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkMode();
        setListener();
        filters();
    }

    private void setListener() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products.brandsStr.clear();
                Products.sizesStr.clear();
                Products.colorsStr.clear();
                Products.categoriesStr.clear();
                Products.min = null;
                Products.max = null;
                Products.isDiscount = 0;
                Products.page = 1;
                Products.isMore = true;
                Products.get().request(1);
                finish();
            }
        });
    }

    private void filters() {
        FilterList filterList = new FilterList(getSupportFragmentManager());
        filterList.productOptionBody = productOptionBody;
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, filterList, filterList.getClass().getSimpleName()).commit();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Colour colour = (Colour) getSupportFragmentManager().findFragmentByTag(Colour.class.getSimpleName());
        if (colour != null && colour.isVisible()) {
            goBack();
            return;
        }

        BrandFilter brandFilter = (BrandFilter) getSupportFragmentManager().findFragmentByTag(BrandFilter.class.getSimpleName());
        if (brandFilter != null && brandFilter.isVisible()) {
            goBack();
            return;
        }

        Category category = (Category) getSupportFragmentManager().findFragmentByTag(Category.class.getSimpleName());
        if (category != null && category.isVisible()) {
            goBack();
            return;
        }

        Cost cost = (Cost) getSupportFragmentManager().findFragmentByTag(Cost.class.getSimpleName());
        if (cost != null && cost.isVisible()) {
            goBack();
            return;
        }

        DefaultFilter defaultFilter = (DefaultFilter) getSupportFragmentManager().findFragmentByTag(DefaultFilter.class.getSimpleName());
        if (defaultFilter != null && defaultFilter.isVisible()) {
            goBack();
            return;
        }

        Razmer razmer = (Razmer) getSupportFragmentManager().findFragmentByTag(Razmer.class.getSimpleName());
        if (razmer != null && razmer.isVisible()) {
            goBack();
            return;
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.store_anim_slide_in_right,
                R.anim.store_anim_slide_out_right);

    }

    private void goBack() {
        FilterList filterListFR = new FilterList(getSupportFragmentManager());
        filterListFR.productOptionBody = productOptionBody;
        Utils.removeShow(filterListFR, filterListFR.getClass().getSimpleName(), getSupportFragmentManager(), R.id.fragmentContainer);
    }


    private void checkMode() {
        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        View view = getWindow().getDecorView();
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;

            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productOptionBody=null;
    }
}