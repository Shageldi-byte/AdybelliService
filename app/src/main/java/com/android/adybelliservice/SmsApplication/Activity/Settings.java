package com.android.adybelliservice.SmsApplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.databinding.SmsActivitySettingsBinding;


public class Settings extends AppCompatActivity {
    private SmsActivitySettingsBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SmsActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        binding.host.setText(Utils.getSharedPreference(context,"socketHost"));
        setListener();
    }

    private void setListener() {
        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setPreference("socketHost",binding.host.getText().toString(),context);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(context, MainActivity.class));
    }
}