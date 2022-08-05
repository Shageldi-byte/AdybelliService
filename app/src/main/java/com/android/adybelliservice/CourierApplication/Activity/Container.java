package com.android.adybelliservice.CourierApplication.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.adybelliservice.CourierApplication.Fragment.Canceled;
import com.android.adybelliservice.CourierApplication.Fragment.OrderDetails;
import com.android.adybelliservice.CourierApplication.Fragment.Profile;
import com.android.adybelliservice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Container extends AppCompatActivity {
    private BottomNavigationView bottom;
    public static Fragment firstFragment=new OrderDetails("ontheway");
    public static Fragment secondFragment=new Canceled();
    public static Fragment thirdFragment=new Profile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courier_activity_container);
        initComponents();
        setBottomNavigation();
    }

    private void setBottomNavigation() {
        changeFragment(firstFragment,firstFragment.getClass().getSimpleName());
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.accepted:
                        changeFragment(new OrderDetails("ontheway"),OrderDetails.class.getSimpleName());
                        return true;
                    case R.id.canceled:
                        changeFragment(new OrderDetails("rejected"),OrderDetails.class.getSimpleName());
                        return true;
                    case R.id.profile:
                        changeFragment(thirdFragment,thirdFragment.getClass().getSimpleName());
                        return true;
                }
                return false;
            }
        });
    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.commit();
    }

    private void initComponents() {
        bottom=findViewById(R.id.bottom);
    }
}