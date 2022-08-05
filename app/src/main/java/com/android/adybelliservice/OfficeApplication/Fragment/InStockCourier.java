package com.android.adybelliservice.OfficeApplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.adybelliservice.OfficeApplication.Adapter.InTmStockAdapter;
import com.android.adybelliservice.OfficeApplication.Api.APIClient;
import com.android.adybelliservice.OfficeApplication.Api.ApiInterface;
import com.android.adybelliservice.OfficeApplication.Common.Utils;
import com.android.adybelliservice.OfficeApplication.Model.Courier;
import com.android.adybelliservice.OfficeApplication.Model.CourierResponse;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderDetailResponse;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.R;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InStockCourier extends Fragment {

    private View view;
    private Context context;
    private ArrayList<OrderDetail> arrayList=new ArrayList<>();
    private RecyclerView rec;
    private ArrayList<Courier> couriers=new ArrayList<>();
    private String status="stock_tm";
    private ProgressBar progress;
    private boolean isLoading=false;
    private Integer count=1;
    private boolean shouldLoadMore=true;
    private SwipeRefreshLayout swipeRefresh;
    private TextView title;
    private LinearLayout empty_container;
    public InStockCourier(String status) {
        this.status=status;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ofis_fragment_in_stock_courier, container, false);
        context=getContext();
        initComponents();
        if(status.equals("stock_tm")){
            title.setText("Stock TM");
        } else if(status.equals("ontheway")){
            title.setText("On delivery");
        } else if(status.equals("received")){
            title.setText("Delivered");
        } else if(status.equals("rejected")){
            title.setText("Canceled");
        }
        setListener();
        request(1);
        return view;
    }

    private void setListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count=1;
                request(1);
                shouldLoadMore=true;
            }
        });

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

    private void request(int i) {
        if(!shouldLoadMore)
        {
            setDefault();
            count=count-1;
            return;
        }
        if(i==1){
            arrayList.clear();
        }
        String last_id="1";
        if(arrayList.size()>0){
            last_id=arrayList.get(arrayList.size()-1).getOd_id();
        }
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Map<String, Object> params = new HashMap<>();
        params.put("last_id",last_id);
        params.put("status",status);
        Call<MyBody<ArrayList<OrderDetail>>> call=apiInterface.getOrderDetails("Bearer "+ Utils.getSharedPreference(context,"tkn"),params);
        String finalLast_id = last_id;
        call.enqueue(new Callback<MyBody<ArrayList<OrderDetail>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<OrderDetail>>> call, Response<MyBody<ArrayList<OrderDetail>>> response) {
                if(response.isSuccessful()){
                    title.setText(status);
                    arrayList.addAll(response.body().getBody());
                    setDefault();
                    if(response.body().getBody().size()<=0){
                        shouldLoadMore=false;
                    }
                    if(finalLast_id.equals("1")){
                        setRecycler();
                        getCouriers();
                    } else{
                        rec.getAdapter().notifyDataSetChanged();
                    }
                } else{
                    setDefault();
//                    Utils.refreshToken(context);
                }

//                if(arrayList==null || arrayList.size()<=0){
//                    empty_container.setVisibility(View.VISIBLE);
//                } else {
//                    empty_container.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<MyBody<ArrayList<OrderDetail>>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                setDefault();
            }
        });
    }

    private void setDefault() {
//        list.setRefreshing(true);
        swipeRefresh.setRefreshing(false);
        progress.setVisibility(View.GONE);
        isLoading=false;
    }

    private void setRecycler(){
        rec.setAdapter(new InTmStockAdapter(context,arrayList,couriers,status));
        rec.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initComponents(){
        rec=view.findViewById(R.id.rec);
        swipeRefresh=view.findViewById(R.id.swipeRefresh);
        progress=view.findViewById(R.id.progress);
        title=view.findViewById(R.id.title);
        empty_container=view.findViewById(R.id.empty_container);
    }

    public void getCouriers(){
        couriers.clear();
        ProgressDialog progressDialog=Utils.progressDialog(context,"Biraz garashyn...","Kurier maglumatlary yuklenyar!",false);
        progressDialog.show();
        ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
        Call<MyBody<ArrayList<Courier>>> call=apiInterface.getCouriers("Bearer "+Utils.getSharedPreference(context,"tkn"));
        call.enqueue(new Callback<MyBody<ArrayList<Courier>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<Courier>>> call, Response<MyBody<ArrayList<Courier>>> response) {
                if(response.isSuccessful()){
                    couriers.addAll(response.body().getBody());
                } else{
//                    Utils.refreshToken(context);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyBody<ArrayList<Courier>>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}