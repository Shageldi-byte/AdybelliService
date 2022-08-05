package com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.adybelliservice.StoreApplication.Adapter.CategoryAdapter.DefaultAdapter;
import com.android.adybelliservice.StoreApplication.Fragment.Product.Products;
import com.android.adybelliservice.R;

import java.util.ArrayList;


public class Cost extends Fragment {

    private View view;
    private ArrayList<com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Cost> arrayList=new ArrayList<>();
    private Context context;
    private RecyclerView rec;
    private TextView title,clear;
    private Button acceptBtn;
    private FragmentManager fragmentManager;
    private EditText min,max;
    public Cost(ArrayList<com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Cost> arrayList, FragmentManager fragmentManager) {
        this.arrayList=arrayList;
        this.fragmentManager=fragmentManager;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.store_fragment_cost, container, false);
        context=getContext();
        initComponents();
        setListeners();
        setFilters();
        setFonts();
        try {
            if (Products.min != null && Products.max != null) {
                max.setText(""+Products.max);
                min.setText(""+Products.min);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return view;
    }

    private void initComponents(){
        rec=view.findViewById(R.id.rec);
        title=view.findViewById(R.id.title);
        clear=view.findViewById(R.id.clear);
        acceptBtn=view.findViewById(R.id.acceptBtn);
        min=view.findViewById(R.id.min);
        max=view.findViewById(R.id.max);
    }

    private void setListeners(){
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(min.getText().toString().isEmpty() || max.getText().toString().isEmpty()){
                    return;
                }
                Products.min=Double.valueOf(min.getText().toString());
                Products.max=Double.valueOf(max.getText().toString());
                Products.isMore=true;
                Products.page=1;
                getActivity().finish();
                Products.get().request(1);
            }
        });

       clear.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Products.min=null;
               Products.max=null;
           }
       });
    }

    private void setFilters(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        DefaultAdapter adapter=new DefaultAdapter(arrayList,context,fragmentManager,min,max);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(adapter);
    }

    private void setFonts(){
    }
}