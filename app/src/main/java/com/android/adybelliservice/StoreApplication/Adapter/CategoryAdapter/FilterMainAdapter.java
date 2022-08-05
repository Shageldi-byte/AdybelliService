package com.android.adybelliservice.StoreApplication.Adapter.CategoryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.BrandFilter;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Category;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Colour;
import com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Razmer;
import com.android.adybelliservice.StoreApplication.Fragment.Product.Products;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Cost;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Default;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.FilterMain;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOptionBody;
import com.android.adybelliservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FilterMainAdapter extends RecyclerView.Adapter<FilterMainAdapter.ViewHolder> {
    private ArrayList<FilterMain> arrayList = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private FragmentManager secondfragmentManager;
    private ProductOptionBody body;

    public FilterMainAdapter(ArrayList<FilterMain> arrayList, Context context, FragmentManager supportFragmentManager, ProductOptionBody body, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.context = context;
        this.fragmentManager = supportFragmentManager;
        this.secondfragmentManager = fragmentManager;
        this.body = body;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_filter_item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FilterMainAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        FilterMain object = arrayList.get(position);
        holder.title.setText(object.getTitle());
        if (position != 0) {
            holder.view.setVisibility(View.GONE);
        }


        if (object.getType().equals("discount")) {
            holder.con1.setVisibility(View.GONE);
            holder.con2.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.GONE);
            holder.stroke.setVisibility(View.GONE);
            if (Products.isDiscount == 1) {
                holder.switch1.setChecked(true);
            }
        }


        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Products.isDiscount = 1;
                } else {
                    Products.isDiscount = 0;
                }
            }
        });


        if (object.getType().equals("Color")) {
            try {
                holder.count.setText("(" + Products.colorsStr.size() + ")");
            } catch (Exception ex) {
                ex.printStackTrace();
                holder.count.setVisibility(View.GONE);
            }
            if(body.getColors() == null || body.getColors().size()<=0) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }

        if (object.getType().equals("Size")) {
            try {
                if(Products.sizesStr.size()>0)
                    holder.count.setText("(" + Products.sizesStr.size() + ")");
                else
                    holder.count.setVisibility(View.GONE);
            } catch (Exception ex) {
                ex.printStackTrace();
                holder.count.setVisibility(View.GONE);
            }
            if(body.getSizes() == null || body.getSizes().size()<=0) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }

        if (object.getType().equals("Category")) {
            try {
                if(Products.categoriesStr.size()>0)
                    holder.count.setText("(" + Products.categoriesStr.size() + ")");
                else
                    holder.count.setVisibility(View.GONE);
            } catch (Exception ex) {
                ex.printStackTrace();
                holder.count.setVisibility(View.GONE);
            }
            if(body.getCategories() == null || body.getCategories().size()<=0) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }

        if (object.getType().equals("Brand")) {
            try {
                if(Products.brandsStr.size()>0)
                    holder.count.setText("(" + Products.brandsStr.size() + ")");
                else
                    holder.count.setVisibility(View.GONE);
            } catch (Exception ex) {
                ex.printStackTrace();
                holder.count.setVisibility(View.GONE);
            }
            if(body.getTrademarks() == null || body.getTrademarks().size()<=0) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }

        if (object.getType().equals("Cost")) {
            try {
                if(Products.min!=null && Products.max!=null)
                    holder.count.setText("(" + Products.min + " - " + Products.max + ")");
                else
                    holder.count.setVisibility(View.GONE);
            } catch (Exception ex) {
                ex.printStackTrace();
                holder.count.setVisibility(View.GONE);
            }
        }

        if (object.getType().equals("Color")) {
            try {
                if(Products.colorsStr.size()>0)
                    holder.count.setText("(" + Products.colorsStr.size()+ ")");
                else
                    holder.count.setVisibility(View.GONE);
            } catch (Exception ex) {
                ex.printStackTrace();
                holder.count.setVisibility(View.GONE);
            }
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (object.getType().equals("Color")) {
                        if (body.getColors() != null) {
                            Colour colour = new Colour(secondfragmentManager);
                            colour.colors = body.getColors();
                            Utils.removeShow(colour,Colour.class.getSimpleName(),fragmentManager,R.id.fragmentContainer);
                        }

                    }
                    if (object.getType().equals("Size")) {
                        if (body.getSizes() != null) {
                            Razmer razmer = new Razmer(secondfragmentManager);
                            razmer.sizes = body.getSizes();
                            Utils.removeShow(razmer,Razmer.class.getSimpleName(),fragmentManager,R.id.fragmentContainer);
                        }

                    }
                    if (object.getType().equals("Category")) {
                        if (body.getCategories() != null) {
                            Category category = new Category(secondfragmentManager);
                            category.arrayList = body.getCategories();
                            Utils.removeShow(category,Category.class.getSimpleName(),fragmentManager,R.id.fragmentContainer);
                        }

                    }
                    if (object.getType().equals("Brand")) {
                        if (body.getTrademarks() != null) {
                            BrandFilter brandFilter = new BrandFilter(secondfragmentManager);
                            brandFilter.arrayList = body.getTrademarks();
                            Utils.removeShow(brandFilter,BrandFilter.class.getSimpleName(),fragmentManager,R.id.fragmentContainer);
                        }

                    }

                    if (object.getType().equals("Cost")) {
                        com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Cost cost=new com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment.Cost(getCost(),secondfragmentManager);
                        Utils.removeShow(cost,cost.getClass().getSimpleName(),fragmentManager,R.id.fragmentContainer);
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, title2, count;
        ImageView arrow;
        View view, stroke;
        LinearLayout con1, con2;
        SwitchCompat switch1;
        RelativeLayout container;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.arrow);
            title = itemView.findViewById(R.id.title);
            title2 = itemView.findViewById(R.id.title2);
            view = itemView.findViewById(R.id.stroke2);
            stroke = itemView.findViewById(R.id.stroke);
            con1 = itemView.findViewById(R.id.con1);
            con2 = itemView.findViewById(R.id.con2);
            switch1 = itemView.findViewById(R.id.switch1);
            container = itemView.findViewById(R.id.container);
            count = itemView.findViewById(R.id.count);
        }
    }

    private ArrayList<Default> getArrayList() {
        ArrayList<Default> defarr = new ArrayList<>();
        defarr.add(new Default(1, "Klassyk", "Category"));
        defarr.add(new Default(1, "Ýöremek üçin", "Category"));
        defarr.add(new Default(1, "Ylgamak üçin", "Category"));
        defarr.add(new Default(1, "Botlar", "Category"));
        defarr.add(new Default(1, "Topuklu", "Category"));
        defarr.add(new Default(1, "Çizme", "Category"));
        defarr.add(new Default(1, "Gar boty", "Category"));
        defarr.add(new Default(1, "Bagjyksyz", "Category"));
        defarr.add(new Default(1, "Garantiýa", "Category"));
        return defarr;
    }

    private ArrayList<Cost> getCost() {
        ArrayList<Cost> defarr = new ArrayList<>();
        for (int i = 0; i < 10000; i += 1000) {
            defarr.add(new Cost(i + 0.0, (i + 1000) + 0.0));
        }
        return defarr;
    }
}
