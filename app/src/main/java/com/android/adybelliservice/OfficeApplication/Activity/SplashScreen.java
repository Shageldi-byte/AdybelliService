package com.android.adybelliservice.OfficeApplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.adybelliservice.R;

public class SplashScreen extends AppCompatActivity {
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ofis_activity_splash_screen);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreen.this,Login.class));
            }
        },5000);
    }
}