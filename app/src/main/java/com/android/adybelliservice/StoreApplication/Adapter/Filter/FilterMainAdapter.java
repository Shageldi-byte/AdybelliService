package com.android.adybelliservice.StoreApplication.Adapter.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.Filter.MainItem;
import com.android.adybelliservice.R;

import java.util.ArrayList;

public class FilterMainAdapter extends RecyclerView.Adapter<FilterMainAdapter.ViewHolder> {
    private ArrayList<MainItem> filters=new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;

    public FilterMainAdapter(ArrayList<MainItem> filters, Context context, FragmentManager fragmentManager) {
        this.filters = filters;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.store_filter_main_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainItem item=filters.get(position);
        holder.title.setText(item.getText_tm());
        if(Utils.getLanguage(context).equals("ru")){
            holder.title.setText(item.getText_ru());
        }
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, title2, count;
        ImageView arrow;
        LinearLayout con1, con2;
        SwitchCompat switch1;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.arrow);
            title = itemView.findViewById(R.id.title);
            title2 = itemView.findViewById(R.id.title2);
            con1 = itemView.findViewById(R.id.con1);
            con2 = itemView.findViewById(R.id.con2);
            switch1 = itemView.findViewById(R.id.switch1);
            container = itemView.findViewById(R.id.container);
            count = itemView.findViewById(R.id.count);
        }
    }
}
