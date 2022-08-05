package com.android.adybelliservice.CourierApplication.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.CourierApplication.Activity.MoreInfo;
import com.android.adybelliservice.CourierApplication.Api.APIClient;
import com.android.adybelliservice.CourierApplication.Api.ApiInterface;
import com.android.adybelliservice.CourierApplication.Common.Utils;
import com.android.adybelliservice.CourierApplication.Database.BoxDB;
import com.android.adybelliservice.CourierApplication.Database.StatusUpdateLogsDB;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;
import com.android.adybelliservice.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InTruckAdapter extends RecyclerView.Adapter<InTruckAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderProduct> orderProducts=new ArrayList<>();
    private String status;
    private BoxDB boxDB;
    private StatusUpdateLogsDB statusUpdateLogsDB;
    public InTruckAdapter(Context context, ArrayList<OrderProduct> orderProducts, String status) {
        this.context = context;
        this.orderProducts = orderProducts;
        this.status = status;
        boxDB=new BoxDB(context);
        statusUpdateLogsDB=new StatusUpdateLogsDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.courier_in_truck, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderProduct object=orderProducts.get(position);
        holder.id.setText("Zakaz kody: "+object.getOrder_id());
        holder.name.setText("Ady: "+object.getName());
        holder.price.setText("Baha: "+object.getPrice());
        holder.size.setText("Razmer: "+object.getSize());
        holder.count.setText("Sany: "+object.getCount()+" / Status: "+object.getStatus());
        Glide.with(context)
                .load(object.getImage())
                .placeholder(R.drawable.courier_placeholder)
                .into(holder.image);


        if(status.equals("rejected") || status.isEmpty()){
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }



        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Uns berin!");
                alert.setMessage("Siz cyndanam bu harydyn statusyny uytgetmek isleyanizmi?");
                alert.setCancelable(true);
                alert.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderDetail("received",object.getDelivery_id()+"",object.getOd_id(),context,holder.getAdapterPosition(),holder);
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

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Uns berin!");
                alert.setMessage("Siz cyndanam bu harydyn statusyny uytgetmek isleyanizmi?");
                alert.setCancelable(true);
                alert.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderDetail("rejected",object.getDelivery_id()+"",object.getOd_id(),context,holder.getAdapterPosition(),holder);
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
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MoreInfo.class);
                intent.putExtra("id",object.getOrder_id());
                context.startActivity(intent);
            }
        });


    }

    public void updateOrderDetail(String status, String delivery_id, String od_id, Context context, int pos, ViewHolder holder){
        if (Utils.getSharedPreference(context,"isInternet").equals("yes")) {
            ProgressDialog progressDialog = Utils.progressDialog(context, "Biraz garashyn...", "Maglumatlar uytgedilyar", false);
            progressDialog.show();
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            EditOrder editOrder = new EditOrder(od_id,status, delivery_id);
            Call<ArrayList<Integer>> call = apiInterface.updateOrderDetail(editOrder, "Bearer " + Utils.getSharedPreference(context, "tkn"));
            call.enqueue(new Callback<ArrayList<Integer>>() {
                @Override
                public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Ustunlikli uytgedildi!", Toast.LENGTH_SHORT).show();
                        orderProducts.remove(pos);
                        notifyItemRemoved(pos);

                    } else {
                        Toast.makeText(context, "Error code: "+response.code(), Toast.LENGTH_SHORT).show();
//                        Utils.refreshToken(context);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                    Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else{
            boolean isUpdate=boxDB.updateStatus(od_id,status);
            if(isUpdate){
                statusUpdateLogsDB.deleteData(od_id);
                statusUpdateLogsDB.insert(od_id,status,delivery_id);
                Toast.makeText(context, "Ustunlikli uytgedildi!", Toast.LENGTH_SHORT).show();
                orderProducts.remove(pos);
                notifyItemRemoved(pos);
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id,name,price,size,count;
        Button accept,reject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            id=itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            size=itemView.findViewById(R.id.size);
            count=itemView.findViewById(R.id.count);
            accept=itemView.findViewById(R.id.accept);
            reject=itemView.findViewById(R.id.reject);
        }
    }
}
