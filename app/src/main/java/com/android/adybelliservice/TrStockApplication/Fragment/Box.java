package com.android.adybelliservice.TrStockApplication.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.TrStockApplication.Activity.TrStockMain;
import com.android.adybelliservice.TrStockApplication.Adapter.BoxAdapter;
import com.android.adybelliservice.TrStockApplication.Api.APIClient;
import com.android.adybelliservice.TrStockApplication.Api.ApiInterface;
import com.android.adybelliservice.TrStockApplication.Model.AddBox;
import com.android.adybelliservice.databinding.FragmentBoxBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Box extends Fragment {

    private FragmentBoxBinding binding;
    private Context context;
    private ArrayList<com.android.adybelliservice.TrStockApplication.Model.Box> boxes=new ArrayList<>();
    private int limit=20;
    private int page=1;
    private boolean isLoading=false;
    private static Box INSTANCE;

    public Box() {
    }

    public static Box newInstance() {
        Box fragment = new Box();
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
        binding=FragmentBoxBinding.inflate(inflater,container,false);
        context=getContext();
        INSTANCE=this;
        request(page);
        setListener();
        return binding.getRoot();
    }

    public static Box get(){
        return INSTANCE;
    }

    private void setListener() {
        binding.rec.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(!binding.rec.canScrollVertically(1) && !isLoading){
                    page++;
                    request(page);
                }
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBox();
            }
        });
    }

    private void addBox() {

        AlertDialog.Builder alert=new AlertDialog.Builder(context);


        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40,40,40,40);

        final EditText titleBox = new EditText(context);
        titleBox.setHint("Name");
        layout.addView(titleBox);

        final EditText descriptionBox = new EditText(context);
        descriptionBox.setHint("Truck name");
        layout.addView(descriptionBox);

        alert.setView(layout);

        alert.setTitle("New box");

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(titleBox.getText().toString().length()>0 && descriptionBox.getText().toString().length()>0) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("Biraz garasyn...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    AddBox addBox=new AddBox(titleBox.getText().toString(),descriptionBox.getText().toString());
                    ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
                    Call<MyBody<String>> call=apiInterface.createBox("Bearer "+Utils.getSharedPreference(context,"tkn"),addBox);
                    call.enqueue(new Callback<MyBody<String>>() {
                        @Override
                        public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                            if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                                page=1;
                                request(page);
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error code: "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<MyBody<String>> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void request(int i) {
        page=i;
        if(page==1){
            boxes.clear();
        }

        isLoading=true;
        TrStockMain.get().showPagination();
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Map<String, Object> params=new HashMap<>();
        params.put("per_page",limit);
        params.put("page",i);
        Call<MyBody<ArrayList<com.android.adybelliservice.TrStockApplication.Model.Box>>> call=apiInterface.getBoxes(
                "Bearer "+ Utils.getSharedPreference(context,"tkn"),
                params
        );
        call.enqueue(new Callback<MyBody<ArrayList<com.android.adybelliservice.TrStockApplication.Model.Box>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<com.android.adybelliservice.TrStockApplication.Model.Box>>> call, Response<MyBody<ArrayList<com.android.adybelliservice.TrStockApplication.Model.Box>>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                    boxes.addAll(response.body().getBody());
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
                TrStockMain.get().hidePagination();
                isLoading=false;
            }

            @Override
            public void onFailure(Call<MyBody<ArrayList<com.android.adybelliservice.TrStockApplication.Model.Box>>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                TrStockMain.get().hidePagination();
                isLoading=false;
            }
        });
    }

    private void setAdapter() {
        binding.rec.setLayoutManager(new LinearLayoutManager(context));
        binding.rec.setAdapter(new BoxAdapter(boxes,context));
    }
}