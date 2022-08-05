package com.android.adybelliservice.StoreApplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Fragment.Main.AddProduct;
import com.android.adybelliservice.StoreApplication.Fragment.Main.Products;
import com.android.adybelliservice.StoreApplication.Fragment.Main.Profile;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Colour;
import com.android.adybelliservice.StoreApplication.Fragment.Orders.MoreInfo;
import com.android.adybelliservice.StoreApplication.Fragment.Orders.Orders;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Context context=this;
    private StoreActivityMainBinding binding;
    public static Fragment firstFragment=new Products();
    public static Fragment secondFragment=new AddProduct();
    public static Fragment thirdFragment=new Orders();
    public static Fragment fourthFragment=new Profile();
    private static MainActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Utils.getSharedPreference(context,"mode").equals("night")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Utils.loadLocal(context);
        INSTANCE=this;
        checkMode();
        binding=StoreActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBottomNavigationItems(context,binding.bottomNavigation);
        setListener();
    }

    public static MainActivity get(){
        return INSTANCE;
    }

    public BottomNavigationView getBottomNavigation(){
        return binding.bottomNavigation;
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

    private void setListener() {
        Utils.hideAdd(new Products(),Products.class.getSimpleName(),getSupportFragmentManager(),R.id.content);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                checkBottomNav(item.getItemId());
                return true;
            }
        });
    }

    private void checkBottomNav(int itemId) {
        switch (itemId){
            case R.id.products:
                Utils.hideAdd(firstFragment,firstFragment.getClass().getSimpleName(),getSupportFragmentManager(),R.id.content);
                changeStatusBarColor(R.color.white);
                break;
            case R.id.add_product:
                Utils.hideAdd(secondFragment,secondFragment.getClass().getSimpleName(),getSupportFragmentManager(),R.id.content);
                changeStatusBarColor(R.color.white);
                break;
            case R.id.orders:
                Utils.hideAdd(thirdFragment,thirdFragment.getClass().getSimpleName(),getSupportFragmentManager(),R.id.content);
                changeStatusBarColor(R.color.main_color);
                break;
            case R.id.user:
                Utils.hideAdd(fourthFragment,fourthFragment.getClass().getSimpleName(),getSupportFragmentManager(),R.id.content);
                changeStatusBarColor(R.color.main_color);
                break;
            default:
                break;

        }
    }

    private void changeStatusBarColor(int color){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(color));
    }

    private void setBottomNavigationItems(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setBottomNavigationItems(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Utils.getMediumFont(context));

            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indexBottomNav", binding.bottomNavigation.getSelectedItemId());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            Integer indexBottomNav = savedInstanceState.getInt("indexBottomNav");
            if (indexBottomNav != null) {
                if(indexBottomNav==R.id.user){
                    fourthFragment=new Profile();
                }
                checkBottomNav(indexBottomNav);
            } else {
                Utils.hideAdd(firstFragment,firstFragment.getClass().getSimpleName(),getSupportFragmentManager(),R.id.content);
            }
        } catch (Exception ex){
            ex.printStackTrace();
            Utils.hideAdd(firstFragment,firstFragment.getClass().getSimpleName(),getSupportFragmentManager(),R.id.content);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firstFragment=new Products();
        secondFragment=new AddProduct();
        thirdFragment=new Orders();
        fourthFragment=new Profile();
    }

    @Override
    public void onBackPressed() {
        MoreInfo moreInfo = (MoreInfo) getSupportFragmentManager().findFragmentByTag(MoreInfo.class.getSimpleName());
        if (moreInfo != null && moreInfo.isVisible()) {
            Utils.removeShow(new Orders(),Orders.class.getSimpleName(),getSupportFragmentManager(),R.id.content);
            thirdFragment=new Orders();
            return;
        }
        Products products = (Products) getSupportFragmentManager().findFragmentByTag(Products.class.getSimpleName());
        if (products != null && products.isVisible()) {
            finish();
            return;
        }
        binding.bottomNavigation.setSelectedItemId(R.id.products);
    }
}