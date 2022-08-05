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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Activity.SelectCategoryActivity;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Fragment.Main.AddProduct;
import com.android.adybelliservice.StoreApplication.Model.Filter.Category;
import com.android.adybelliservice.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Category> filters = new ArrayList<>();
    private Context context;

    public CategoryAdapter(ArrayList<Category> filters, Context context) {
        this.filters = filters;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Category item = filters.get(position);
        if (item.getTitle() != null)
            holder.title.setText(item.getTitle() + "");
        if (Utils.getLanguage(context).equals("ru")) {
            if (item.getTitle_ru() != null)
                holder.title.setText(item.getTitle_ru() + "");
        }

        if (item.getSub_categories() == null || item.getSub_categories().size() <= 0) {
            holder.arrowAccordion.setVisibility(View.GONE);
        } else {
            holder.arrowAccordion.setVisibility(View.VISIBLE);
            holder.subs.setAdapter(new SubCategoryAdapter(item.getSub_categories(),context));
            holder.subs.setLayoutManager(new LinearLayoutManager(context));
        }

//        if (item.getCat_id() != null && AddProduct.selected_category.contains(item.getCat_id())) {
//            holder.title.setTextColor(context.getResources().getColor(R.color.main_color));
//            holder.arrow.setVisibility(View.VISIBLE);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SelectCategoryActivity.selected_category.size()>1){
                    SelectCategoryActivity.selected_category.remove(1);
                }
                SelectCategoryActivity.selected_category.add(item.getCat_id());
                if (item.getSub_categories() == null || item.getSub_categories().size() <= 0) {
                    holder.arrowAccordion.setVisibility(View.GONE);
                    if (AddProduct.selected_category.contains(item.getCat_id())) {
                        AddProduct.selected_category.remove(item.getCat_id() + "");
                        holder.title.setTextColor(context.getResources().getColor(R.color.black));
                        holder.arrow.setVisibility(View.GONE);
                    } else {
                        AddProduct.selected_category.add(item.getCat_id() + "");
                        holder.title.setTextColor(context.getResources().getColor(R.color.main_color));
                        holder.arrow.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.arrowAccordion.setVisibility(View.VISIBLE);
                    if(holder.arrowAccordion.getRotation()==180){
                        holder.arrowAccordion.setRotation(270);
                        holder.subs.setVisibility(View.VISIBLE);
                    } else {
                        holder.subs.setVisibility(View.GONE);
                        holder.arrowAccordion.setRotation(180);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView arrow,arrowAccordion;
        LinearLayout con1;
        RelativeLayout container;
        private RecyclerView subs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.arrow);
            title = itemView.findViewById(R.id.title);
            con1 = itemView.findViewById(R.id.con1);
            container = itemView.findViewById(R.id.container);
            arrowAccordion = itemView.findViewById(R.id.arrowAccordion);
            subs = itemView.findViewById(R.id.subs);
        }
    }
}
