package com.android.adybelliservice.CourierApplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.CourierApplication.Activity.MoreInfo;
import com.android.adybelliservice.CourierApplication.Model.Order;
import com.android.adybelliservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CanceledAdapter extends RecyclerView.Adapter<CanceledAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orders=new ArrayList<>();

    public CanceledAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.courier_order_design, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CanceledAdapter.ViewHolder holder, int position) {
        Order order=orders.get(position);
        if(order.getStatus().equals("come_back")){
            setAllUnVisible(holder);
            holder.on_the_way.setVisibility(View.VISIBLE);
            holder.firstBtn.setText("Inkär et");
            holder.secondBtn.setText("Kabul et");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MoreInfo.class);
                context.startActivity(intent);
            }
        });

    }

    private void setAllUnVisible(ViewHolder holder) {
        holder.canceled.setVisibility(View.GONE);
        holder.delivered.setVisibility(View.GONE);
        holder.on_the_way.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout on_the_way;
        private Button delivered;
        private Button canceled;
        private Button firstBtn;
        private Button secondBtn;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            canceled=itemView.findViewById(R.id.canceled);
            delivered=itemView.findViewById(R.id.delivered);
            on_the_way=itemView.findViewById(R.id.on_the_way);
            secondBtn=itemView.findViewById(R.id.secondBtn);
            firstBtn=itemView.findViewById(R.id.firstBtn);
        }
    }
}
