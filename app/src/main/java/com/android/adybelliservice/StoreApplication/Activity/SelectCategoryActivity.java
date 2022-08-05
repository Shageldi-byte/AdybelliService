package com.android.adybelliservice.StoreApplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.adybelliservice.StoreApplication.Adapter.AddProduct.MainCategoryAdapter;
import com.android.adybelliservice.StoreApplication.Model.Filter.Category;
import com.android.adybelliservice.StoreApplication.Model.Filter.MainCategory;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreActivitySelectCategoryBinding;

import java.util.ArrayList;

public class SelectCategoryActivity extends AppCompatActivity {
    private Context context = this;
    public static ArrayList<Category> filters = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<MainCategory> mainCategories = new ArrayList<>();
    private ArrayList<MainCategory> finalCategories = new ArrayList<>();
    private ArrayList<MainCategory> lastCategories = new ArrayList<>();
    private StoreActivitySelectCategoryBinding binding;
    public static ArrayList<String> selected_category = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.store_anim_slide_in_left,
                R.anim.store_anim_slide_out_left);
        binding = StoreActivitySelectCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkMode();
        setListener();
        ArrayList<String> checkedParents = new ArrayList<>();
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getParent_id() != null && !checkedParents.contains(filters.get(i).getParent_id())) {
                checkedParents.add(filters.get(i).getParent_id());
                ArrayList<Category> tempSubCategories = new ArrayList<>();
                String cur_p_id=filters.get(i).getParent_id();
                for (int j = 0; j < filters.size(); j++) {
                    if (cur_p_id.equals(filters.get(j).getParent_id())) {
                        tempSubCategories.add(filters.get(j));
                    }
                }
                mainCategories.add(new MainCategory(filters.get(i).getCat_id(), "", "", filters.get(i).getParent_id(), tempSubCategories));
            }
        }
        checkedParents.clear();
        for (MainCategory mainCategory : mainCategories) {
            for (int i = 0; i < filters.size(); i++) {
                if (filters.get(i).getParent_id() != null && mainCategory.getParent_id().equals(filters.get(i).getCat_id())) {
                    finalCategories.add(new MainCategory(filters.get(i).getCat_id(), filters.get(i).getTitle(),
                            filters.get(i).getTitle_ru(),
                            filters.get(i).getParent_id(),
                            mainCategory.getSub_categories()));
                }
            }
        }


        for (int i = 0; i < filters.size(); i++) {
            if(filters.get(i).getParent_id()==null){
                ArrayList<Category> tempSubCategories = new ArrayList<>();
                for (MainCategory finalCategory : finalCategories) {
                    if(filters.get(i).getCat_id().equals(finalCategory.getParent_id())){
                        tempSubCategories.add(new Category(finalCategory.getId(),finalCategory.getTitle(),
                                finalCategory.getTitle_ru(),finalCategory.getParent_id(),finalCategory.getSub_categories()));
                    }
                }
                lastCategories.add(new MainCategory(filters.get(i).getCat_id(),
                        filters.get(i).getTitle(),
                        filters.get(i).getTitle_ru(),
                        filters.get(i).getParent_id(),
                        tempSubCategories));
                Log.e("Result",filters.get(i).getTitle()+" / "+tempSubCategories.get(0).getSub_categories().size());
            }
        }


        setFilters();
    }


    private void setListener() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        ((Activity)context).setResult(132, intent);
        ((Activity)context).finish();
        overridePendingTransition(R.anim.store_anim_slide_in_right,
                R.anim.store_anim_slide_out_right);
    }

    private void setFilters() {
        binding.rec.setAdapter(new MainCategoryAdapter(lastCategories, context,getSupportFragmentManager()));
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
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
        selected_category.clear();
    }
}