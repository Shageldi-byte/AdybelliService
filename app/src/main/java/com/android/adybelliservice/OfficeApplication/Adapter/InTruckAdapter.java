package com.android.adybelliservice.OfficeApplication.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Api.APIClient;
import com.android.adybelliservice.OfficeApplication.Api.ApiInterface;
import com.android.adybelliservice.OfficeApplication.Common.Utils;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InTruckAdapter extends RecyclerView.Adapter<InTruckAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderProduct> orderProducts=new ArrayList<>();

    public InTruckAdapter(Context context, ArrayList<OrderProduct> orderProducts) {
        this.context = context;
        this.orderProducts = orderProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.ofis_in_truck, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderProduct object=orderProducts.get(position);
        holder.id.setText("ID: "+object.getOd_id());
        holder.name.setText("Ady: "+object.getName());
        holder.price.setText("Baha: "+object.getPrice());
        holder.size.setText("Razmer: "+object.getSize());
        holder.count.setText("Sany: "+object.getCount()+" / Status: "+object.getStatus());
        Glide.with(context)
                .load(object.getImage())
                .placeholder(R.drawable.ofis_placeholder)
                .into(holder.image);

        if(object.getStatus().equals("ontheway") || object.getStatus().equals("rejected") || object.getStatus().equals("stock_tm") || object.getStatus().equals("received")){
            holder.setTmStock.setVisibility(View.GONE);
            holder.notReceived.setVisibility(View.GONE);
        } else {
            holder.setTmStock.setVisibility(View.VISIBLE);
            holder.notReceived.setVisibility(View.VISIBLE);
        }



        holder.setTmStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Uns berin!");
                alert.setMessage("Siz cyndanam bu harydyn statusyny uytgetmek isleyanizmi?");
                alert.setCancelable(true);
                alert.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderDetail("stock_tm","0",object.getOd_id(),context,holder.getAdapterPosition(),holder);
                    }
                });
                alert.setNegativeButton("Yok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });

        holder.notReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Uns berin!");
                alert.setMessage("Siz cyndanam bu harydyn statusyny uytgetmek isleyanizmi?");
                alert.setCancelable(true);
                alert.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderDetail("rejected","0",object.getOd_id(),context,holder.getAdapterPosition(),holder);
                    }
                });
                alert.setNegativeButton("Yok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });


    }

    public void updateOrderDetail(String status, String delivery_id, String od_id, Context context, int pos, ViewHolder holder){
        ProgressDialog progressDialog= Utils.progressDialog(context,"Biraz garashyn...","Maglumatlar uytgedilyar",false);
        progressDialog.show();
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        EditOrder editOrder=new EditOrder(od_id,status,delivery_id);
        Call<MyBody<String>> call=apiInterface.updateOrderDetail(editOrder,"Bearer "+Utils.getSharedPreference(context,"tkn"));
        call.enqueue(new Callback<MyBody<String>>() {
            @Override
            public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Ustunlikli uytgedildi!", Toast.LENGTH_SHORT).show();
                    try {
                        orderProducts.get(pos).setDelivery_id(Integer.parseInt(delivery_id));
                    } catch (Exception ex){
                        ex.printStackTrace();
                        orderProducts.get(pos).setDelivery_id(null);
                    }

                    orderProducts.get(pos).setStatus(status);
                    notifyItemChanged(pos);

                } else{
                    Log.e("Error: ","code: "+response.code()+", msg:"+response.message());
//                    Utils.refreshToken(context);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyBody<String>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id,name,price,size,count;
        Button setTmStock,notReceived;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            id=itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            size=itemView.findViewById(R.id.size);
            count=itemView.findViewById(R.id.count);
            setTmStock=itemView.findViewById(R.id.setTmStock);
            notReceived=itemView.findViewById(R.id.notReceived);
        }
    }
}
