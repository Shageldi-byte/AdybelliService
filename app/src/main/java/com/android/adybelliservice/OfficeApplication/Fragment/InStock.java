package com.android.adybelliservice.OfficeApplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.adybelliservice.OfficeApplication.Activity.QRScanner;
import com.android.adybelliservice.OfficeApplication.Adapter.InStockAdapter;
import com.android.adybelliservice.OfficeApplication.Api.APIClient;
import com.android.adybelliservice.OfficeApplication.Api.ApiInterface;
import com.android.adybelliservice.OfficeApplication.Common.Constant;
import com.android.adybelliservice.OfficeApplication.Common.Utils;
import com.android.adybelliservice.OfficeApplication.Model.InStockModel;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InStock extends Fragment {


    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<InStockModel> arrayList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private boolean isFirstTime=true;
    private ImageView qr;
    private Integer page=1;
    private Integer limit=20;
    private EditText search;
    private ProgressBar progress;
    private Boolean isLoading=false;
    private LinearLayout empty_container;
    public InStock() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ofis_fragment_in_stock, container, false);
        context=getContext();
        initComponents();
        isFirstTime=true;
        refreshBox();
        setListener();

        return view;
    }

    private void setListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                refreshBox();
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, QRScanner.class));
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1) && !isLoading){
                    page++;
                    refreshBox();
                }
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    page=1;
                    refreshBox();
                    return true;
                }
                return false;
            }
        });
    }

    private void refreshBox() {
        setRecycler(search.getText().toString());
    }

    private void setRecycler(String query) {

        ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
        if(query.isEmpty()) {
            HashMap<String,Object> queries = new HashMap<>();
            queries.put("page",page);
            queries.put("per_page",limit);
            queries.put("status", Constant.IN_TRUCK);
            Call<MyBody<ArrayList<InStockModel>>> call = apiInterface.getBoxes("Bearer " + Utils.getSharedPreference(context, "tkn"), queries);
            setCall(call);
        } else {
            HashMap<String,Object> queries = new HashMap<>();
            queries.put("page",page);
            queries.put("per_page",limit);
            queries.put("status", Constant.IN_TRUCK);
            queries.put("text", query);
            Call<MyBody<ArrayList<InStockModel>>> call = apiInterface.getBoxes("Bearer " + Utils.getSharedPreference(context, "tkn"), queries);
            setCall(call);
        }




    }

    private void setCall(Call<MyBody<ArrayList<InStockModel>>> call) {
        ProgressDialog progressDialog=Utils.progressDialog(context,"Biraz garashyn...","Box maglumatlary yuklenyar!",false);
        progress.setVisibility(View.VISIBLE);
        if(page==1){
            progressDialog.show();
            progress.setVisibility(View.GONE);
            arrayList.clear();
        }
        call.enqueue(new Callback<MyBody<ArrayList<InStockModel>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<InStockModel>>> call, Response<MyBody<ArrayList<InStockModel>>> response) {
                if(response.isSuccessful()){
                    arrayList.addAll(response.body().getBody());
                    if(page==1){
                        setBoxes();
                    } else {
                        try{
                            recyclerView.getAdapter().notifyDataSetChanged();
                        } catch (Exception ex){
                            ex.printStackTrace();
                            setBoxes();
                        }
                    }


                } else{
                    Toast.makeText(context, "Error code: "+response.code(), Toast.LENGTH_SHORT).show();
//                    Utils.refreshToken(context);
                }
                if(arrayList==null || arrayList.size()<=0){
                    empty_container.setVisibility(View.VISIBLE);
                } else {
                    empty_container.setVisibility(View.GONE);
                }
                swipeRefresh.setRefreshing(false);
                progressDialog.dismiss();
                progress.setVisibility(View.GONE);
                isLoading=false;
            }

            @Override
            public void onFailure(Call<MyBody<ArrayList<InStockModel>>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                swipeRefresh.setRefreshing(false);
                progress.setVisibility(View.GONE);
                isLoading=false;
            }
        });
    }

    private void setBoxes() {
        InStockAdapter adapter=new InStockAdapter(arrayList,context,getFragmentManager());
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        isFirstTime=false;

    }

    private void initComponents() {
        recyclerView=view.findViewById(R.id.rec);
        swipeRefresh=view.findViewById(R.id.swipeRefresh);
        qr=view.findViewById(R.id.qr);
        search=view.findViewById(R.id.search);
        progress=view.findViewById(R.id.progress);
        empty_container=view.findViewById(R.id.empty_container);
    }
}