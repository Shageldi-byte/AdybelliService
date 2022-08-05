package com.android.adybelliservice.TrStockApplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.TrStockApplication.Adapter.BoxedOrderDetailAdapter;
import com.android.adybelliservice.TrStockApplication.Adapter.OrderedOrderDetailAdapter;
import com.android.adybelliservice.TrStockApplication.Api.APIClient;
import com.android.adybelliservice.TrStockApplication.Api.ApiInterface;
import com.android.adybelliservice.TrStockApplication.Model.Box;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;
import com.android.adybelliservice.databinding.ActivityBoxViewBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoxView extends AppCompatActivity {
    private ActivityBoxViewBinding binding;
    private Context context=this;
    private int page=1;
    private int per_page=20;
    private String box_id;
    private ArrayList<OrderDetail> arrayList=new ArrayList<>();
    private boolean isLoading=true;
    private static BoxView INSTANCE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBoxViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        INSTANCE=this;
        box_id=getIntent().getStringExtra("id");
        setListener();
        request(page);
    }

    public static BoxView get(){
        return INSTANCE;
    }

    public ArrayList<OrderDetail> getArrayList(){
        return arrayList;
    }

    public void request(int i) {
        page=i;
        if(page==1){
            arrayList.clear();
        }

        isLoading=true;
        showPagination();
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Map<String, Object> params=new HashMap<>();
        params.put("per_page",per_page);
        params.put("page",i);
        params.put("box_id",box_id);
        Call<MyBody<ArrayList<OrderDetail>>> call=apiInterface.getBoxedProducts(
                "Bearer "+ Utils.getSharedPreference(context,"tkn"),
                params
        );
        call.enqueue(new Callback<MyBody<ArrayList<OrderDetail>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<OrderDetail>>> call, Response<MyBody<ArrayList<OrderDetail>>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                    arrayList.addAll(response.body().getBody());
                    if(i==1){
                        setAdapter();
                    } else {
                        try{
                            binding.rec.getAdapter().notifyDataSetChanged();
                        } catch (Exception ex){
                            ex.printStackTrace();
                            setAdapter();
                        }
                    }
                    Log.e("page",i+"");
                } else {
                    Toast.makeText(context, ""+response.code(), Toast.LENGTH_SHORT).show();
                }
                hidePagination();
                isLoading=false;
            }

            @Override
            public void onFailure(Call<MyBody<ArrayList<OrderDetail>>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                TrStockMain.get().hidePagination();
                isLoading=false;
            }
        });
    }

    private void hidePagination() {
        binding.pagination.setVisibility(View.GONE);
    }

    private void showPagination() {
        binding.pagination.setVisibility(View.VISIBLE);
    }

    private void setAdapter() {
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
        binding.rec.setAdapter(new BoxedOrderDetailAdapter(arrayList,context,getSupportFragmentManager(),box_id));

    }

    private void setListener() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rec.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(!binding.rec.canScrollVertically(1) && !isLoading){
                    page++;
                    request(page);
                }
            }
        });
    }
}