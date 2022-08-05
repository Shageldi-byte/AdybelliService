package com.android.adybelliservice.OfficeApplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Activity.SetCourier;
import com.android.adybelliservice.OfficeApplication.Model.InStockCourierModel;
import com.android.adybelliservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InStockCourierAdapter extends RecyclerView.Adapter<InStockCourierAdapter.ViewHolder> {
    private ArrayList<InStockCourierModel> arrayList=new ArrayList<>();
    private Context context;

    public InStockCourierAdapter(ArrayList<InStockCourierModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.ofis_in_stock_courier, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InStockCourierAdapter.ViewHolder holder, int position) {
        InStockCourierModel model=arrayList.get(position);
        holder.date.setText(model.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SetCourier.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
        }
    }
}
