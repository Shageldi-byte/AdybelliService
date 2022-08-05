package com.android.adybelliservice.OfficeApplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Model.Courier;
import com.android.adybelliservice.OfficeApplication.Model.Products;
import com.android.adybelliservice.R;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private ArrayList<Products> arrayList = new ArrayList<>();
    private Context context;
    private ArrayList<Courier> couriers = new ArrayList<>();

    public ProductsAdapter(ArrayList<Products> arrayList, Context context, ArrayList<Courier> couriers) {
        this.arrayList = arrayList;
        this.context = context;
        this.couriers = couriers;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ofis_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductsAdapter.ViewHolder holder, int position) {
        Products object = arrayList.get(position);
        holder.id.setText("Detail ID: " + object.getId());
        holder.brand.setText("Brand: " + object.getBrand());
        holder.name.setText("Name: " + object.getName());
        holder.price.setText("Price: " + object.getPrice());
        holder.size.setText("Size: " + object.getSize());

        Glide.with(context)
                .load(object.getImage())
                .placeholder(R.drawable.ofis_placeholder)
                .into(holder.image);

        holder.setCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(holder);
            }
        });
    }

    private void showDialog(@NotNull ViewHolder holder) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ofis_select_courier_dialog);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
        RecyclerView rec = dialog.findViewById(R.id.rec);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button ok = dialog.findViewById(R.id.ok);
        CourierAdapter courierAdapter = new CourierAdapter(couriers, context,null, false);
        rec.setAdapter(courierAdapter);
        rec.setLayoutManager(new LinearLayoutManager(context));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Snackbar.make(holder.itemView, "Üstünlikli ýatda saklandy!", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id, name, price, size, brand;
        Button setCourier;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            setCourier = itemView.findViewById(R.id.setCourier);
            image = itemView.findViewById(R.id.image);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            size = itemView.findViewById(R.id.size);
            brand = itemView.findViewById(R.id.brand);
        }
    }
}
