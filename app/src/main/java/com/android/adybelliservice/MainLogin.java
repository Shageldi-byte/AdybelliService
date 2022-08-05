package com.android.adybelliservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.adybelliservice.OfficeApplication.Activity.Login;
import com.android.adybelliservice.SmsApplication.Activity.MainActivity;
import com.android.adybelliservice.StoreApplication.Activity.LoginActivity;
import com.android.adybelliservice.TrStockApplication.Activity.TrStockMain;

public class MainLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main_login);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("AndroidId",androidId);

    }

    public void startStore(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void startSms(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void startCourier(View v) {
        startActivity(new Intent(this, com.android.adybelliservice.CourierApplication.Activity.MainActivity.class));
    }

    public void startOffice(View v) {
        startActivity(new Intent(this, Login.class));
    }

    public void startTrStock(View v) {
        startActivity(new Intent(this, com.android.adybelliservice.TrStockApplication.Activity.Login.class));
    }
}