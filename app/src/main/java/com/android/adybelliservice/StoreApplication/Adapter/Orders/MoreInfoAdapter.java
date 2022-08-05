package com.android.adybelliservice.StoreApplication.Adapter.Orders;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;


import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.Constant;
import com.android.adybelliservice.StoreApplication.Common.OrderStatus;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserSingleOrderProducts;
import com.android.adybelliservice.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MoreInfoAdapter extends RecyclerView.Adapter<MoreInfoAdapter.ViewHolder> {
    private ArrayList<GetUserSingleOrderProducts> moreInfos = new ArrayList<>();
    private Context context;
    private ApiInterface apiInterface;
    private String status = "";

    public MoreInfoAdapter(ArrayList<GetUserSingleOrderProducts> moreInfos, Context context) {
        this.moreInfos = moreInfos;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_order_info_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MoreInfoAdapter.ViewHolder holder, int position) {
        GetUserSingleOrderProducts object = moreInfos.get(position);


        holder.old_cost.setPaintFlags(holder.old_cost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        holder.name.setText(object.getTrademark());
        String nameText = object.getName();

        if (Utils.getLanguage(context).equals("ru")) {
            nameText = object.getName_ru();
        }
        holder.sizeTitle.setText(nameText + ", " + Utils.getStringResource(R.string.razmer, context) + " - " + object.getSize());
        holder.cost.setText(object.getPrice() + " TMT");

        holder.count.setText(Utils.getStringResource(R.string.count, context) + " " + object.getCount());



        if(object.getStatus().equals(OrderStatus.PENDING)){
            holder.canceled.setEnabled(true);
            holder.accepted.setEnabled(true);
            holder.canceled.setVisibility(View.VISIBLE);
            holder.accepted.setVisibility(View.VISIBLE);
        } else {

            if (object.getStatus().equals("canceled") || object.getStatus().equals("rejected") || object.getStatus().equals("refund")) {
                holder.canceled.setChecked(true);
                holder.accepted.setChecked(false);
                holder.accepted.setVisibility(View.GONE);
            } else if (object.getStatus().equals("accepted")){
                holder.canceled.setChecked(false);
                holder.accepted.setChecked(true);
                holder.canceled.setVisibility(View.GONE);
            } else {
                holder.canceled.setVisibility(View.GONE);
                holder.accepted.setVisibility(View.GONE);
            }

            holder.canceled.setEnabled(false);
            holder.accepted.setEnabled(false);

        }

        holder.accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.accepted.isChecked()){
                    moreInfos.get(holder.getAdapterPosition()).setStatus(OrderStatus.ACCEPTED);
                }
            }
        });

        holder.canceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.canceled.isChecked()){
                    moreInfos.get(holder.getAdapterPosition()).setStatus(OrderStatus.CANCELED);
                }
            }
        });




        Glide.with(context)
                .load(Constant.SERVER_URL + object.getImage())
                .placeholder(R.drawable.store_placeholder)
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> imgs = new ArrayList<>();
                imgs.add(object.getImage());
//                Util.showImageViewer(context, imgs, imgs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moreInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, sizeTitle, old_cost, cost, count;
        ImageView image, statusImage;
        Button cancelProduct;
        AppCompatRadioButton canceled,accepted;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            sizeTitle = itemView.findViewById(R.id.sizeTitle);
            old_cost = itemView.findViewById(R.id.old_cost);
            cost = itemView.findViewById(R.id.cost);
            image = itemView.findViewById(R.id.image);
            count = itemView.findViewById(R.id.count);
            accepted = itemView.findViewById(R.id.accepted);
            canceled = itemView.findViewById(R.id.canceled);
        }
    }
}
