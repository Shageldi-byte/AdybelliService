package com.android.adybelliservice.TrStockApplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.adybelliservice.R;
import com.android.adybelliservice.TrStockApplication.Fragment.Box;
import com.android.adybelliservice.TrStockApplication.Fragment.Home;
import com.android.adybelliservice.TrStockApplication.Fragment.Received;
import com.android.adybelliservice.databinding.TrActivityTrStockMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.TreeMap;

public class TrStockMain extends AppCompatActivity {
    private TrActivityTrStockMainBinding binding;
    private Context context=this;
    private static TrStockMain INSTANCE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=TrActivityTrStockMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListener();
        INSTANCE=this;
    }

    public static TrStockMain get(){
        return INSTANCE;
    }

    public LinearProgressIndicator getPagination(){
        return binding.pagination;
    }

    public void showPagination(){
        binding.pagination.setVisibility(View.VISIBLE);
    }

    public void hidePagination(){
        binding.pagination.setVisibility(View.GONE);
    }



    private void setListener() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content,Home.newInstance()).commit();
        binding.bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,Home.newInstance()).commit();
                        break;
                    case R.id.received:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, Received.newInstance()).commit();
                        break;
                    case R.id.box:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, Box.newInstance()).commit();
                        break;
                }
                return true;
            }
        });
    }
}