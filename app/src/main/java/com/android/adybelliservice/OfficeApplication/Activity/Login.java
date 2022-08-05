package com.android.adybelliservice.OfficeApplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.adybelliservice.OfficeApplication.Api.ApiInterface;
import com.android.adybelliservice.OfficeApplication.Api.APIClient;
import com.android.adybelliservice.OfficeApplication.Common.Utils;
import com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Button regist;
    private EditText username,password;
    private ApiInterface apiInterface;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.ofis_activity_login);
        initComponents();
        setListener();
        username.setText(Utils.getSharedPreference(context,"email"));
    }

    private void setListener() {
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()){
                    Toast.makeText(Login.this, "Maglumatlary girizin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressDialog progressDialog=Utils.progressDialog(context,"Biraz garashyn...","",true);
                progressDialog.show();
                apiInterface= APIClient.getClient().create(ApiInterface.class);
                Call<MyBody<LoginResponse>> call=apiInterface.login(new com.android.adybelliservice.OfficeApplication.Model.Auth.Login(username.getText().toString(),password.getText().toString()));
                call.enqueue(new Callback<MyBody<LoginResponse>>() {
                    @Override
                    public void onResponse(Call<MyBody<LoginResponse>> call, Response<MyBody<LoginResponse>> response) {
                        if(response.isSuccessful()){
                            if(!response.body().getBody().getToken().isEmpty()){
                                Utils.setSharedPreference(context,"tkn",response.body().getBody().getToken());
                                Utils.setSharedPreference(context,"email",response.body().getBody().getEmail());
                                Utils.setSharedPreference(context,"user_id",response.body().getBody().getUser_id());
                                Utils.setSharedPreference(context,"name",response.body().getBody().getName());
                                Utils.setSharedPreference(context,"surname",response.body().getBody().getSurname());
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(context,MainActivity.class));
                            }
                        } else {
                            progressDialog.dismiss();
                            try {
                                Toast.makeText(context, response.body().getMessage().getText(), Toast.LENGTH_SHORT).show();
                            } catch (Exception ex){
                                ex.printStackTrace();
                                Toast.makeText(context, response.code()+"", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyBody<LoginResponse>> call, Throwable t) {
                        Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    private void initComponents() {
        password=findViewById(R.id.password);
        regist=findViewById(R.id.regist);
        username=findViewById(R.id.username);
    }
}