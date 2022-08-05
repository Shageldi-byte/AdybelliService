package com.android.adybelliservice.TrStockApplication.Adapter;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.TrStockApplication.Api.APIClient;
import com.android.adybelliservice.TrStockApplication.Api.ApiInterface;
import com.android.adybelliservice.TrStockApplication.Model.ChangeOrderStatusOrdered;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private ArrayList<OrderDetail> arrayList = new ArrayList<>();
    private Context context;

    public OrderDetailAdapter(ArrayList<OrderDetail> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tr_stock_home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail item = arrayList.get(holder.getAbsoluteAdapterPosition());
        Glide.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.courier_placeholder)
                .into(holder.image);
        holder.name.setText("Name: "+item.getName());
        holder.orderId.setText("Order id: "+item.getOrder_id());
        holder.size.setText("Size: "+item.getSize());
        holder.site.setText("Site: "+"Flo");
        holder.product_id.setText("Product id: "+item.getProd_id());
        holder.price.setText("Price: "+item.getPrice());
        holder.cost.setText("Cost price: "+item.getCost_price());

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setTitle("Biraz garashyn...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
                ChangeOrderStatusOrdered body=new ChangeOrderStatusOrdered(item.getOd_id(),item.getOrder_id());
                Call<MyBody<String>> call=apiInterface.changeOrderStatusCanceled("Bearer "+ Utils.getSharedPreference(context,"tkn"),body);
                call.enqueue(new Callback<MyBody<String>>() {
                    @Override
                    public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            arrayList.remove(holder.getAbsoluteAdapterPosition());
                            notifyItemRemoved(holder.getAbsoluteAdapterPosition());
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
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setTitle("Biraz garashyn...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
                ChangeOrderStatusOrdered body=new ChangeOrderStatusOrdered(item.getOd_id(),item.getOrder_id());
                Call<MyBody<String>> call=apiInterface.changeOrderStatusOrdered("Bearer "+ Utils.getSharedPreference(context,"tkn"),body);
                call.enqueue(new Callback<MyBody<String>>() {
                    @Override
                    public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Ordered", Toast.LENGTH_SHORT).show();
                            arrayList.remove(holder.getAbsoluteAdapterPosition());
                            notifyItemRemoved(holder.getAbsoluteAdapterPosition());
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
        });

        holder.gotoSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = item.getOrigin_url();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCheckBox check;
        private ImageView image;
        private TextView orderId,name,size,site,product_id,price,cost;
        private MaterialButton cancel,accept,gotoSite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check=itemView.findViewById(R.id.check);
            image=itemView.findViewById(R.id.image);
            orderId=itemView.findViewById(R.id.orderId);
            name=itemView.findViewById(R.id.name);
            size=itemView.findViewById(R.id.size);
            site=itemView.findViewById(R.id.site);
            product_id=itemView.findViewById(R.id.product_id);
            price=itemView.findViewById(R.id.price);
            cost=itemView.findViewById(R.id.cost);
            cancel=itemView.findViewById(R.id.cancel);
            accept=itemView.findViewById(R.id.accept);
            gotoSite=itemView.findViewById(R.id.gotoSite);
        }
    }
}
