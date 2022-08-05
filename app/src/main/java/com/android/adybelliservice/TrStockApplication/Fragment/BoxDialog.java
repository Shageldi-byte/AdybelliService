package com.android.adybelliservice.TrStockApplication.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.TrStockApplication.Api.APIClient;
import com.android.adybelliservice.TrStockApplication.Api.ApiInterface;
import com.android.adybelliservice.TrStockApplication.Model.ChangeOrderStatusOrdered;
import com.android.adybelliservice.TrStockApplication.Model.OrderToBox;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BoxDialog extends DialogFragment {
    public static String selectedBoxId="";
    private MaterialButton acceptButton;
    private View view;
    private Context context;
    private String od_id;
    public BoxDialog(String od_id) {
        this.od_id=od_id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getChildFragmentManager().beginTransaction().replace(R.id.frame,new Box()).commit();
        context=getContext();
        view=inflater.inflate(R.layout.fragment_box_dialog, container, false);
        acceptButton=view.findViewById(R.id.accept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedBoxId.isEmpty()){
                    Toast.makeText(context, "Select box", Toast.LENGTH_SHORT).show();
                } else {
                    ProgressDialog progressDialog=new ProgressDialog(context);
                    progressDialog.setTitle("Biraz garashyn...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
                    OrderToBox body=new OrderToBox(od_id,selectedBoxId);
                    Call<MyBody<String>> call=apiInterface.orderToBox("Bearer "+ Utils.getSharedPreference(context,"tkn"),body);
                    call.enqueue(new Callback<MyBody<String>>() {
                        @Override
                        public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                            if(response.isSuccessful()){
                                Received.get().getArrayList().clear();
                                Received.get().request();
                                dismiss();
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
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}