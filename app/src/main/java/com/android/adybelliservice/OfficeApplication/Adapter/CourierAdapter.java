package com.android.adybelliservice.OfficeApplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Model.Courier;
import com.android.adybelliservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {
    private ArrayList<Courier> arrayList=new ArrayList<>();
    private Context context;
    private ImageView oldCheck;
    private TextView oldText;
    public static String selectedDeliverID=null;
    private Integer deliveryId=null;
    private boolean isSelectable=true;
    public CourierAdapter(ArrayList<Courier> arrayList, Context context, Integer deliveryId, boolean b) {
        this.arrayList = arrayList;
        this.context = context;
        this.deliveryId=deliveryId;
        this.isSelectable=b;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.ofis_courier, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CourierAdapter.ViewHolder holder, int position) {
        Courier model=arrayList.get(position);
        String deliveryName=model.getName();
        if(model.getSurname()!=null){
            deliveryName += " "+model.getSurname();
        }
        holder.date.setText(model.getName());

        holder.phone.setText("Telefon belgisi: "+model.getPhone());
        if(deliveryId!=null) {
            if (deliveryId == model.getId()) {
                if (oldCheck != null) {
                    oldText.setTextColor(Color.BLACK);
                    oldCheck.setVisibility(View.GONE);
                }
                holder.check.setVisibility(View.VISIBLE);
                holder.date.setTextColor(context.getResources().getColor(R.color.second));
                oldCheck = holder.check;
                oldText = holder.date;
                selectedDeliverID = model.getId()+"";
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelectable){
                    return;
                }
                if(oldCheck!=null){
                    oldText.setTextColor(Color.BLACK);
                    oldCheck.setVisibility(View.GONE);
                }
                holder.check.setVisibility(View.VISIBLE);
                holder.date.setTextColor(context.getResources().getColor(R.color.second));
                oldCheck=holder.check;
                oldText=holder.date;
                selectedDeliverID=model.getId()+"";
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,phone;
        ImageView check;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            check=itemView.findViewById(R.id.check);
            phone=itemView.findViewById(R.id.phone);
        }
    }
}
