package com.android.adybelliservice.OfficeApplication.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Adapter.InTruckAdapter;
import com.android.adybelliservice.OfficeApplication.Api.APIClient;
import com.android.adybelliservice.OfficeApplication.Api.ApiInterface;
import com.android.adybelliservice.OfficeApplication.Common.Utils;
import com.android.adybelliservice.OfficeApplication.Model.Courier;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.R;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeProducts extends AppCompatActivity {
    private ArrayList<OrderProduct> arrayList=new ArrayList<>();
    private Context context=this;
    private RecyclerView rec;
    private ArrayList<Courier> couriers=new ArrayList<>();
    private String boxId="";
    private ProgressBar progress;
    private boolean isLoading=false;
    private Integer count=1;
    private boolean shouldLoadMore=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ofis_activity_see_products);
        boxId=getIntent().getStringExtra("id");
        initComponents();
        setListener();
        request(1);


    }

    private void setListener() {
        rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading)
                        loadMore();

                }
            }
        });
    }

    private void loadMore() {
        if (isLoading) {
            isLoading=true;
            try {
                progress.setVisibility(View.VISIBLE);
                count = count+1;
                request(count);
            } catch (Exception ex) {
                ex.printStackTrace();
                isLoading=false;
            }
        }
    }

    private void request(int i) {
        if(!shouldLoadMore)
        {
            return;
        }
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("box_id",boxId);
        params.put("per_page",20);
        params.put("page",i);
        Call<MyBody<ArrayList<OrderProduct>>> call=apiInterface.getBoxedProducts("Bearer "+ Utils.getSharedPreference(context,"tkn"),params);
        call.enqueue(new Callback<MyBody<ArrayList<OrderProduct>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<OrderProduct>>> call, Response<MyBody<ArrayList<OrderProduct>>> response) {
                if(response.isSuccessful()){
                    arrayList.addAll(response.body().getBody());
                    setDefault();
                    if(response.body().getBody().size()<=0){
                        shouldLoadMore=false;
                    }
                    if(i==1){
                        setRecycler();
                    } else{
                        try {
                            rec.getAdapter().notifyDataSetChanged();
                        } catch (Exception ex){
                            ex.printStackTrace();
                            setRecycler();
                        }
                    }
                } else{
                    setDefault();
                    Toast.makeText(context, "Error"+response.code(), Toast.LENGTH_SHORT).show();
//                    Utils.refreshToken(context);
                }
            }

            @Override
            public void onFailure(Call<MyBody<ArrayList<OrderProduct>>> call, Throwable t) {
                setDefault();
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefault() {
//        list.setRefreshing(true);
        progress.setVisibility(View.GONE);
        isLoading=false;
    }

    private void initComponents() {
        rec=findViewById(R.id.rec);
        progress=findViewById(R.id.progress);
    }

    private void setRecycler(){
        rec.setAdapter(new InTruckAdapter(context,arrayList));
        rec.setLayoutManager(new LinearLayoutManager(context));
    }
}