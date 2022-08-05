package com.android.adybelliservice.StoreApplication.Adapter.AddProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Fragment.Main.AddProduct;
import com.android.adybelliservice.StoreApplication.Model.Filter.Brands;
import com.android.adybelliservice.R;

import java.util.ArrayList;

public class DefaultItemAdapter extends RecyclerView.Adapter<DefaultItemAdapter.ViewHolder> {
    private ArrayList<Brands> items=new ArrayList<>();
    private Context context;
    private ViewHolder oldHolder=null;

    public DefaultItemAdapter(ArrayList<Brands> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.store_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Brands item=items.get(holder.getAdapterPosition());
        holder.arrow.setVisibility(View.GONE);
        holder.title.setText(item.getTitle()+"");
        holder.arrowAccordion.setVisibility(View.GONE);
        if(AddProduct.selected_brand.equals(item.getTm_id())){
            holder.arrow.setVisibility(View.VISIBLE);
            holder.title.setTextColor(context.getResources().getColor(R.color.main_color));
            oldHolder=holder;
        } else {
            holder.arrow.setVisibility(View.GONE);
            holder.title.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldHolder!=null) {
                    oldHolder.arrow.setVisibility(View.GONE);
                    oldHolder.title.setTextColor(context.getResources().getColor(R.color.black));
                }
                AddProduct.selected_brand=item.getTm_id();
                AddProduct.selected_brand_title=item.getTitle();
                holder.arrow.setVisibility(View.VISIBLE);
                holder.title.setTextColor(context.getResources().getColor(R.color.main_color));
                oldHolder=holder;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView arrow,arrowAccordion;
        LinearLayout con1;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.arrow);
            arrowAccordion = itemView.findViewById(R.id.arrowAccordion);
            title = itemView.findViewById(R.id.title);
            con1 = itemView.findViewById(R.id.con1);
            container = itemView.findViewById(R.id.container);
        }
    }
}
