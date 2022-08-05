package com.android.adybelliservice.OfficeApplication.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Api.APIClient;
import com.android.adybelliservice.OfficeApplication.Api.ApiInterface;
import com.android.adybelliservice.OfficeApplication.Common.Constant;
import com.android.adybelliservice.OfficeApplication.Common.Utils;
import com.android.adybelliservice.OfficeApplication.Model.Courier;
import com.android.adybelliservice.OfficeApplication.Model.EditAddress;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.R;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InTmStockAdapter extends RecyclerView.Adapter<InTmStockAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderDetail> orderProducts = new ArrayList<>();
    private ArrayList<Courier> couriers = new ArrayList<>();
    private String status;

    public InTmStockAdapter(Context context, ArrayList<OrderDetail> orderProducts, ArrayList<Courier> couriers, String status) {
        this.context = context;
        this.orderProducts = orderProducts;
        this.couriers = couriers;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ofis_stock_tm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail object = orderProducts.get(position);
        holder.id.setText("ID: " + object.getOd_id());
        holder.name.setText("Ady: " + object.getName());
        holder.price.setText("Baha: " + object.getPrice());
        holder.size.setText("Razmer: " + object.getSize());
        holder.count.setText("Sany: " + object.getCount());
        Glide.with(context)
                .load(object.getImage())
                .placeholder(R.drawable.ofis_placeholder)
                .into(holder.image);


        if (status.equals("stock_tm")) {
            holder.setTmStock.setVisibility(View.VISIBLE);
            holder.notReceived.setVisibility(View.GONE);
            holder.editAddress.setVisibility(View.VISIBLE);
        } else if (status.equals("ontheway")) {
            holder.setTmStock.setVisibility(View.VISIBLE);
            holder.notReceived.setVisibility(View.VISIBLE);
            holder.editAddress.setVisibility(View.GONE);
            if (object.getDelivery_id() != null) {
                holder.setTmStock.setVisibility(View.GONE);
                holder.notReceived.setVisibility(View.VISIBLE);
            } else {
                holder.setTmStock.setVisibility(View.VISIBLE);
                holder.notReceived.setVisibility(View.GONE);
            }
        } else {
            holder.setTmStock.setVisibility(View.GONE);
            holder.notReceived.setVisibility(View.GONE);
            holder.editAddress.setVisibility(View.GONE);
        }


        holder.setTmStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCourier(holder, object.getOd_id(), object.getDelivery_id());
            }
        });


        holder.notReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Uns berin!");
                alert.setMessage("Siz cyndanam bu harydy kurierden ayyrmak isleyanizmi? Haryt yenede Stock TM bolumine geciriler!");
                alert.setCancelable(true);
                alert.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateOrderDetail("stock_tm", "0", object.getOd_id(), context, holder.getAdapterPosition(), holder);
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

        holder.editAddress.setVisibility(View.GONE);

        holder.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(holder,object.getOrder_id(),holder.getAdapterPosition());
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCourier(holder, object.getOd_id(), object.getDelivery_id());

            }
        });

    }

    public void updateOrderDetail(String status, String delivery_id, String od_id, Context context, int pos, ViewHolder holder) {
        ProgressDialog progressDialog = Utils.progressDialog(context, "Biraz garashyn...", "Maglumatlar uytgedilyar", false);
        progressDialog.show();
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        EditOrder editOrder = new EditOrder(od_id,status, delivery_id);
        Call<MyBody<String>> call = apiInterface.updateOrderDetail(editOrder, "Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<MyBody<String>>() {
            @Override
            public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Ustunlikli uytgedildi!", Toast.LENGTH_SHORT).show();
                    orderProducts.remove(pos);
                    notifyItemRemoved(pos);
                    Snackbar.make(holder.itemView, "Harydyn statusy uytgedildi!", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                } else {
//                    Utils.refreshToken(context);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyBody<String>> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, editAddress;
        TextView id, name, price, size, count;
        Button setTmStock, notReceived;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            size = itemView.findViewById(R.id.size);
            count = itemView.findViewById(R.id.count);
            setTmStock = itemView.findViewById(R.id.setTmStock);
            notReceived = itemView.findViewById(R.id.notReceived);
            editAddress = itemView.findViewById(R.id.editAddress);
        }
    }

    private void showDialog(ViewHolder holder,String id,Integer pos) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ofis_edit_address);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        Button save = dialog.findViewById(R.id.save);
        Button cancel = dialog.findViewById(R.id.cancel);
        EditText edit_text = dialog.findViewById(R.id.edit_text);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_text.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Address girizin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressDialog progressDialog = Utils.progressDialog(context, "Biraz garashyn...", "Maglumatlar uytgedilyar", false);
                progressDialog.show();
                ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
                EditAddress editAddress=new EditAddress(orderProducts.get(pos).getStatus(),edit_text.getText().toString());
                Call<ArrayList<Integer>> call = apiInterface.editAddress(id,"Bearer "+Utils.getSharedPreference(context,"tkn"),editAddress);
                call.enqueue(new Callback<ArrayList<Integer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                        if(response.isSuccessful()){
                            dialog.dismiss();
                            Snackbar.make(holder.itemView, "Üstünlikli ýatda saklandy!", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    }).show();
                        } else{
//                            Utils.refreshToken(context);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                        Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void setCourier(@NotNull ViewHolder holder, String od_id, Integer deliveryId) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ofis_select_courier_dialog);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
        RecyclerView rec = dialog.findViewById(R.id.rec);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button ok = dialog.findViewById(R.id.ok);

        if (!status.equals("stock_tm")) {
            ok.setVisibility(View.GONE);
            CourierAdapter courierAdapter = new CourierAdapter(couriers, context, deliveryId, false);
            rec.setAdapter(courierAdapter);
            rec.setLayoutManager(new LinearLayoutManager(context));
        } else {
            CourierAdapter courierAdapter = new CourierAdapter(couriers, context, deliveryId, true);
            rec.setAdapter(courierAdapter);
            rec.setLayoutManager(new LinearLayoutManager(context));
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CourierAdapter.selectedDeliverID != null) {
                    updateOrderDetail("ontheway", CourierAdapter.selectedDeliverID, od_id, context, holder.getAdapterPosition(), holder);
                } else {
                    Toast.makeText(context, "Korier saylan!", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();

            }
        });
        dialog.show();
    }
}
