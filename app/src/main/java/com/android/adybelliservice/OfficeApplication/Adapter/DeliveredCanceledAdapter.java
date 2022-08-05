package com.android.adybelliservice.OfficeApplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Model.Products;
import com.android.adybelliservice.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DeliveredCanceledAdapter extends RecyclerView.Adapter<DeliveredCanceledAdapter.ViewHolder> {
    private ArrayList<Products> arrayList=new ArrayList<>();
    private Context context;

    public DeliveredCanceledAdapter(ArrayList<Products> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.ofis_product, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeliveredCanceledAdapter.ViewHolder holder, int position) {
        Products object=arrayList.get(position);
        holder.id.setText("Detail ID: "+object.getId());
        holder.brand.setText("Brand: "+object.getBrand());
        holder.name.setText("Name: "+object.getName());
        holder.price.setText("Price: "+object.getPrice());
        holder.size.setText("Size: "+object.getSize());
        holder.setCourier.setVisibility(View.GONE);

        Glide.with(context)
                .load(object.getImage())
                .placeholder(R.drawable.ofis_placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id,name,price,size,brand;
        Button setCourier;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            setCourier=itemView.findViewById(R.id.setCourier);
            image=itemView.findViewById(R.id.image);
            id=itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            size=itemView.findViewById(R.id.size);
            brand=itemView.findViewById(R.id.brand);
        }
    }
}
