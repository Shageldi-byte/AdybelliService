package com.android.adybelliservice.OfficeApplication.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.adybelliservice.OfficeApplication.Fragment.Canceled;
import com.android.adybelliservice.OfficeApplication.Fragment.Delivered;
import com.android.adybelliservice.OfficeApplication.Fragment.InStock;
import com.android.adybelliservice.OfficeApplication.Fragment.InStockCourier;
import com.android.adybelliservice.R;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    private SmoothBottomBar bottomBar;
    private Fragment firstFragment = new InStock();
    private Fragment thirdFragment = new Delivered();
    private Fragment fourthFragment = new Canceled();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ofis_activity_main);
        initComponents();
        setListener();
    }



    private void setListener() {
        changeFragment(firstFragment, firstFragment.getClass().getSimpleName());
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        changeFragment(firstFragment, firstFragment.getClass().getSimpleName());
                        return true;
                    case 1:
                        changeFragment(new InStockCourier("stock_tm"), InStockCourier.class.getSimpleName());
                        return true;
                    case 2:
                        changeFragment(new InStockCourier("ontheway"), InStockCourier.class.getSimpleName());
                        return true;
                    case 3:
                        changeFragment(new InStockCourier("received"), InStockCourier.class.getSimpleName());
                        return true;
                    case 4:
                        changeFragment(new InStockCourier("rejected"), InStockCourier.class.getSimpleName());
                        return true;
                }
                return false;
            }
        });
    }

    private void initComponents() {
        bottomBar = findViewById(R.id.bottomBar);
    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.commit();
    }
}