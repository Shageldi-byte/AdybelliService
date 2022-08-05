package com.android.adybelliservice.StoreApplication.Adapter.AddProduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Activity.SelectCategoryActivity;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Fragment.Main.AddProduct;
import com.android.adybelliservice.StoreApplication.Model.Filter.Category;
import com.android.adybelliservice.R;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private ArrayList<Category> filters = new ArrayList<>();
    private Context context;

    public SubCategoryAdapter(ArrayList<Category> filters, Context context) {
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

        holder.arrowAccordion.setVisibility(View.GONE);


        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size));

//        if (item.getCat_id() != null && AddProduct.selected_category.contains(item.getCat_id())) {
//            holder.title.setTextColor(context.getResources().getColor(R.color.main_color));
//            holder.arrow.setVisibility(View.VISIBLE);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddProduct.selected_category.contains(item.getCat_id())) {
                    AddProduct.selected_category.remove(item.getCat_id() + "");
                    holder.title.setTextColor(context.getResources().getColor(R.color.black));
                    holder.arrow.setVisibility(View.GONE);
                } else {
                    AddProduct.selected_category.add(item.getCat_id() + "");
                    holder.title.setTextColor(context.getResources().getColor(R.color.main_color));
                    holder.arrow.setVisibility(View.VISIBLE);
                }


                SelectCategoryActivity.selected_category.add(item.getCat_id());
                AddProduct.selected_category.clear();
                AddProduct.selected_category.addAll(SelectCategoryActivity.selected_category);
                Intent intent = new Intent();
                ((Activity)context).setResult(132, intent);
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.store_anim_slide_in_right,
                        R.anim.store_anim_slide_out_right);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView arrow, arrowAccordion;
        LinearLayout con1;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.arrow);
            title = itemView.findViewById(R.id.title);
            con1 = itemView.findViewById(R.id.con1);
            container = itemView.findViewById(R.id.container);
            arrowAccordion = itemView.findViewById(R.id.arrowAccordion);
        }
    }
}
