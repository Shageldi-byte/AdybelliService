package com.android.adybelliservice.TrStockApplication.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.TrStockApplication.Activity.TrStockMain;
import com.android.adybelliservice.TrStockApplication.Adapter.OrderDetailAdapter;
import com.android.adybelliservice.TrStockApplication.Adapter.OrderedOrderDetailAdapter;
import com.android.adybelliservice.TrStockApplication.Api.APIClient;
import com.android.adybelliservice.TrStockApplication.Api.ApiInterface;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;
import com.android.adybelliservice.databinding.FragmentReceivedBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Received extends Fragment {

    private Context context;
    private String status="ordered";
    private ArrayList<OrderDetail> arrayList=new ArrayList<>();
    private FragmentReceivedBinding binding;
    private boolean isLoading=false;
    private static Received INSTANCE;
    public Received() {
    }

    public static Received newInstance() {
        Received fragment = new Received();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        binding=FragmentReceivedBinding.inflate(inflater,container,false);
        INSTANCE=this;
        request();
        setListener();
        return binding.getRoot();
    }

    public static Received get(){
        return INSTANCE;
    }

    public ArrayList<OrderDetail> getArrayList(){
        return arrayList;
    }

    public void request() {
        isLoading=true;
        TrStockMain.get().showPagination();
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        String last_id="1";
        if(arrayList.size()>0){
            last_id=arrayList.get(arrayList.size()-1).getOd_id();
        }
        Map<String, Object> params=new HashMap<>();
        params.put("last_id",last_id);
        params.put("status",status);
        Call<MyBody<ArrayList<OrderDetail>>> call= apiInterface.getOrderDetails("Bearer "+ Utils.getSharedPreference(context,"tkn"),params);
        String finalLast_id = last_id;
        call.enqueue(new Callback<MyBody<ArrayList<OrderDetail>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<OrderDetail>>> call, Response<MyBody<ArrayList<OrderDetail>>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                    arrayList.addAll(response.body().getBody());
                    if(finalLast_id.equals("1")){
                        setAdapter();
                    } else {
                        try{
                            binding.rec.getAdapter().notifyDataSetChanged();
                        } catch (Exception ex){
                            ex.printStackTrace();
                            setAdapter();
                        }
                    }
                } else {
                    Toast.makeText(context, "Error code: "+response.code(), Toast.LENGTH_SHORT).show();
                }
                TrStockMain.get().hidePagination();
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

    private void setAdapter() {
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
        binding.rec.setAdapter(new OrderedOrderDetailAdapter(arrayList,context,getActivity().getSupportFragmentManager()));
    }

    private void setListener() {
        binding.addToBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        binding.rec.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(!binding.rec.canScrollVertically(1) && !isLoading){
                    request();
                }
            }
        });
    }
}