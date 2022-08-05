package com.android.adybelliservice.StoreApplication.Adapter.CategoryAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.adybelliservice.StoreApplication.Fragment.Product.Products;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Size;
import com.android.adybelliservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {
    private ArrayList<Size> sizes = new ArrayList<>();
    private Context context;

    public SizeAdapter(ArrayList<Size> colors, Context context) {
        this.sizes = colors;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_size_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SizeAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Size size = sizes.get(position);
        holder.btn.setText(size.getSize() + "");
        if (Products.sizesStr.contains(size.getId() + ""))
        {
            Log.e("Size", Arrays.toString(Products.sizesStr.toArray()) + " / " + size.getId() + "");
            holder.btn.setBackgroundColor(context.getResources().getColor(R.color.main_color));
            holder.btn.setTextColor(context.getResources().getColor(R.color.always_white));

        }


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Products.sizesStr.contains(size.getId()+"")) {
                    holder.btn.setBackgroundColor(context.getResources().getColor(R.color.background));
                    holder.btn.setTextColor(context.getResources().getColor(R.color.black));
                    Products.sizesStr.remove(size.getId() + "");
                } else {
                    holder.btn.setBackgroundColor(context.getResources().getColor(R.color.main_color));
                    holder.btn.setTextColor(context.getResources().getColor(R.color.always_white));
                    Products.sizesStr.add(size.getId() + "");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sizes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.sizeBtn);
        }
    }
}
