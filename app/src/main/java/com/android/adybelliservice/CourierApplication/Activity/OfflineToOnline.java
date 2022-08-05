package com.android.adybelliservice.CourierApplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.adybelliservice.CourierApplication.Api.APIClient;
import com.android.adybelliservice.CourierApplication.Api.ApiInterface;
import com.android.adybelliservice.CourierApplication.Common.Utils;
import com.android.adybelliservice.CourierApplication.Database.StatusUpdateLogsDB;
import com.android.adybelliservice.OfficeApplication.Model.EditAddress;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;
import com.android.adybelliservice.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineToOnline extends AppCompatActivity {
    private Context context=this;
    private Button send;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courier_activity_offline_to_online);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offlineToOnline();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(context, Container.class));
    }

    private void offlineToOnline() {
        ProgressDialog progressDialog=Utils.progressDialog(context,"Biraz garashyn...","Maglumatlar serwera ugradylyar!",false);
        StatusUpdateLogsDB statusUpdateLogsDB=new StatusUpdateLogsDB(context);
        Cursor cursor=statusUpdateLogsDB.getAll();
        if(cursor.getCount()>0){

            progressDialog.show();
            while (cursor.moveToNext()){
                ApiInterface apiInterface2= APIClient.getClient().create(ApiInterface.class);
                String status=cursor.getString(2);
                String did=cursor.getString(3);
                String od_id=cursor.getString(1);
                EditOrder editOrder = new EditOrder(od_id,status,did);
                Call<ArrayList<Integer>> call = apiInterface2.updateOrderDetail(editOrder, "Bearer " + Utils.getSharedPreference(context, "tkn"));
                call.enqueue(new Callback<ArrayList<Integer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                        if(response.isSuccessful()){
                            statusUpdateLogsDB.deleteData(od_id);
                            k++;
                            if(cursor.getCount()==k){
                                progressDialog.dismiss();
                                Toast.makeText(context, "Ustunlikli ugradyldy!", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(context, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}