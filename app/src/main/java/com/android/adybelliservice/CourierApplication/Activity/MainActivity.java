package com.android.adybelliservice.CourierApplication.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.adybelliservice.CourierApplication.Api.APIClient;
import com.android.adybelliservice.CourierApplication.Api.ApiInterface;
import com.android.adybelliservice.CourierApplication.Common.Utils;
import com.android.adybelliservice.CourierApplication.Database.StatusUpdateLogsDB;
import com.android.adybelliservice.CourierApplication.Model.Auth.LoginResponse;
import com.android.adybelliservice.CourierApplication.Model.Auth.Me;
import com.android.adybelliservice.OfficeApplication.Model.Auth.Login;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button next;
    private EditText username,password;
    private ApiInterface apiInterface;
    private Context context=this;
    private CheckBox withoutInternet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.courier_activity_main);
        initComponents();
        setListener();
        username.setText(Utils.getSharedPreference(context,"email"));



       
    }

    private void setListener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Maglumatlary girizin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!withoutInternet.isChecked()) {
                    Utils.setSharedPreference(context, "isInternet", "yes");
                    ProgressDialog progressDialog = Utils.progressDialog(context, "Biraz garashyn...", "", true);
                    progressDialog.show();
                    apiInterface = APIClient.getClient().create(ApiInterface.class);
                    Call<MyBody<com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse>> call = apiInterface.login(new Login(username.getText().toString(), password.getText().toString()));
                    call.enqueue(new Callback<MyBody<com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse>>() {
                        @Override
                        public void onResponse(Call<MyBody<com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse>> call, Response<MyBody<com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse>> response) {
                            if (response.isSuccessful()) {
                                if (!response.body().getBody().getToken().isEmpty()) {
                                    Log.e("Courier token",response.body().getBody().getToken());
                                    Utils.setSharedPreference(context, "tkn", response.body().getBody().getToken());
//                                    Utils.setSharedPreference(context, "refresh_tkn", response.body().getBody().getToken());
                                    Utils.setSharedPreference(context, "email", username.getText().toString());
                                    Utils.setSharedPreference(context, "fullname", response.body().getBody().getName()+" "+response.body().getBody().getSurname());
                                    Utils.setSharedPreference(context, "password", password.getText().toString());
                                    Utils.setSharedPreference(context, "email", response.body().getBody().getEmail());
//                                    authMe(progressDialog);
                                    Utils.setSharedPreference(context, "id", response.body().getBody().getUser_id());
                                    progressDialog.dismiss();
                                    finishProcess();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Nadogry maglumatlar girizdiniz! CODE:"+response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MyBody<com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse>> call, Throwable t) {
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    Utils.setSharedPreference(context, "isInternet", "no");
                    if(username.getText().toString().equals(Utils.getSharedPreference(context,"email")) && password.getText().toString().equals(Utils.getSharedPreference(context,"password"))){
                        finish();
                        startActivity(new Intent(context, Container.class));
                    } else {
                        Toast.makeText(context, "Nadogry maglumatlar girizdiniz", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void authMe(ProgressDialog progressDialog) {
        ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
        Call<Me> call=apiInterface.authMe("Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<Me>() {
            @Override
            public void onResponse(Call<Me> call, Response<Me> response) {
                if(response.isSuccessful()){
                    Utils.setSharedPreference(context, "id", response.body().getId());
                    progressDialog.dismiss();
                    finishProcess();
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Me> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void finishProcess() {
        Cursor cursor=new StatusUpdateLogsDB(context).getAll();
        if(cursor.getCount()>0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Uns berin!");
            alert.setMessage("Siz offline uytgedilen maglumatlary serwere ugratmak isleyanizmi?");
            alert.setCancelable(true);
            alert.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                    startActivity(new Intent(context, OfflineToOnline.class));
                }
            });
            alert.setNegativeButton("Yok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                    startActivity(new Intent(context, Container.class));
                }
            });
            alert.show();
        } else{
            finish();
            startActivity(new Intent(context, Container.class));
        }
    }


    private void initComponents() {
        next=findViewById(R.id.next);
        password=findViewById(R.id.password);
        username=findViewById(R.id.username);
        withoutInternet=findViewById(R.id.withoutInternet);
    }


}