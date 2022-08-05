package com.android.adybelliservice.StoreApplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.GBody;
import com.android.adybelliservice.StoreApplication.Model.Login.LoginBody;
import com.android.adybelliservice.StoreApplication.Model.Login.LoginRequest;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private StoreActivityLoginBinding binding;
    private Context context=this;
    private ApiInterface apiInterface;
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
        checkMode();
        binding=StoreActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFonts(context,binding.root);
        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        if(binding.login.getText().toString().length()<=0 || binding.password.getText().toString().length()<=0){
            Toast.makeText(context, getResources().getString(R.string.fill_out), Toast.LENGTH_SHORT).show();
            return;
        }
        binding.ok.setVisibility(View.GONE);
        binding.loadingButton.setVisibility(View.VISIBLE);
        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<GBody<LoginBody>> call=apiInterface.signIn(new LoginRequest(binding.login.getText().toString(),binding.password.getText().toString()),"5c249d738fc4a4ae6f2844c0b744e06a");
        call.enqueue(new Callback<GBody<LoginBody>>() {
            @Override
            public void onResponse(Call<GBody<LoginBody>> call, Response<GBody<LoginBody>> response) {
                if(response.isSuccessful()){
                    Utils.setPreference("name",response.body().getBody().getName()+"",context);
                    Utils.setPreference("surname",response.body().getBody().getSurname()+"",context);
                    Utils.setPreference("phone",response.body().getBody().getPhone()+"",context);
                    Utils.setPreference("user_id",response.body().getBody().getUser_id()+"",context);
                    Utils.setPreference("role",response.body().getBody().getRole()+"",context);
                    Utils.setPreference("gender",response.body().getBody().getGender()+"",context);
                    Utils.setPreference("email",response.body().getBody().getEmail()+"",context);
                    Utils.setPreference("date_birth",response.body().getBody().getDate_birth()+"",context);
                    Utils.setPreference("token",response.body().getBody().getToken()+"",context);
                    Intent intent=new Intent(context,MainActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        String msg = Utils.checkMessage(response.body().getMessage(), context);
                        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG).show();

                    } catch (Exception ex){
                        ex.printStackTrace();
                        Snackbar.make(binding.getRoot(), getResources().getString(R.string.something_went_wrong),Snackbar.LENGTH_LONG).show();
                    }
                }
                binding.ok.setVisibility(View.VISIBLE);
                binding.loadingButton.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GBody<LoginBody>> call, Throwable t) {
                binding.ok.setVisibility(View.VISIBLE);
                binding.loadingButton.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(),t.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });

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

    private void setFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                if(v instanceof TextInputLayout){
                    ((TextInputLayout) v).setTypeface(Utils.getRegularFont(context));
                }
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setFonts(context, child);
                }
            } else if (v instanceof TextInputEditText) {
                ((TextInputEditText) v).setTypeface(Utils.getRegularFont(context));

            }
        } catch (Exception e) {
        }
    }
}