package com.android.adybelliservice.TrStockApplication.Adapter;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.R;
import com.android.adybelliservice.TrStockApplication.Activity.TrStockMain;
import com.android.adybelliservice.TrStockApplication.Fragment.Box;
import com.android.adybelliservice.TrStockApplication.Fragment.BoxDialog;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

public class OrderedOrderDetailAdapter extends RecyclerView.Adapter<OrderedOrderDetailAdapter.ViewHolder> {
    private ArrayList<OrderDetail> arrayList = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    public OrderedOrderDetailAdapter(ArrayList<OrderDetail> arrayList, Context context, FragmentManager childFragmentManager) {
        this.arrayList = arrayList;
        this.context = context;
        this.fragmentManager=childFragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tr_stock_ordered_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail item = arrayList.get(position);
        Glide.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.courier_placeholder)
                .into(holder.image);
        holder.name.setText("Name: "+item.getName());
        holder.orderId.setText("Order id: "+item.getOrder_id());
        holder.size.setText("Size: "+item.getSize());
        holder.site.setText("Site: "+"Flo");
        holder.product_id.setText("Product id: "+item.getProd_id());

        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(item,holder,holder.getAbsoluteAdapterPosition());
            }
        });
    }

    private void show(OrderDetail item, ViewHolder holder, int absoluteAdapterPosition) {
        new BoxDialog(item.getOd_id()).show(fragmentManager,BoxDialog.class.getSimpleName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCheckBox check;
        private ImageView image;
        private TextView orderId,name,size,site,product_id;
        private MaterialButton box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check=itemView.findViewById(R.id.check);
            image=itemView.findViewById(R.id.image);
            orderId=itemView.findViewById(R.id.orderId);
            name=itemView.findViewById(R.id.name);
            size=itemView.findViewById(R.id.size);
            site=itemView.findViewById(R.id.site);
            product_id=itemView.findViewById(R.id.product_id);
            box=itemView.findViewById(R.id.box);
        }
    }
}
