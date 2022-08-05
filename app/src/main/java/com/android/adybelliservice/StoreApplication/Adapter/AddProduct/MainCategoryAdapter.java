package com.android.adybelliservice.StoreApplication.Adapter.AddProduct;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Activity.SelectCategoryActivity;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.Filter.Category;
import com.android.adybelliservice.StoreApplication.Model.Filter.MainCategory;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreSelectCategoryDialogBinding;

import java.util.ArrayList;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {
    private ArrayList<MainCategory> mainCategories=new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    public MainCategoryAdapter(ArrayList<MainCategory> mainCategories, Context context, FragmentManager supportFragmentManager) {
        this.mainCategories = mainCategories;
        this.context = context;
        this.fragmentManager=supportFragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.store_main_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainCategory mainCategory=mainCategories.get(position);
        holder.title.setText(mainCategory.getTitle());
        if(Utils.getLanguage(context).equals("ru")){
            if(mainCategory.getTitle_ru()!=null)
                holder.title.setText(mainCategory.getTitle_ru());
        }

        if(mainCategory.getSub_categories()!=null && mainCategory.getSub_categories().size()>0){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectCategoryActivity.selected_category.clear();
                    SelectCategoryActivity.selected_category.add(mainCategory.getId());
                    showDialog(mainCategory.getSub_categories(),holder.title.getText().toString());
                }
            });
        }

    }

    private void showDialog(ArrayList<Category> sub_categories, String s){
        Dialog dialog = new Dialog(context);
        StoreSelectCategoryDialogBinding selectDialogBinding = StoreSelectCategoryDialogBinding.inflate(LayoutInflater.from(context));
        selectDialogBinding.title.setText(s);
        dialog.setContentView(selectDialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        selectDialogBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        selectDialogBinding.rec.setAdapter(new CategoryAdapter(sub_categories,context));
        selectDialogBinding.rec.setLayoutManager(new LinearLayoutManager(context));
        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mainCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView arrow;
        LinearLayout con1;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.arrow);
            title = itemView.findViewById(R.id.title);
            con1 = itemView.findViewById(R.id.con1);
            container = itemView.findViewById(R.id.container);
        }
    }
}
