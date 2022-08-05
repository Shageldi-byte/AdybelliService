package com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.adybelliservice.StoreApplication.Fragment.Product.Products;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Default;
import com.android.adybelliservice.R;

import java.util.ArrayList;


public class DefaultFilter extends Fragment {

    private View view;
    private ArrayList<Default> arrayList=new ArrayList<>();
    private Context context;
    private RecyclerView rec;
    private TextView title,clear;
    private Button acceptBtn;
    private String titleStr;
    private FragmentManager fragmentManager;
    public DefaultFilter(ArrayList<Default> arrayList,String titleStr,FragmentManager fragmentManager) {
        this.arrayList=arrayList;
        this.titleStr=titleStr;
        this.fragmentManager=fragmentManager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.store_fragment_default_filter, container, false);
        context=getContext();
        initComponents();
        setListeners();
        setFilters();
        setFonts();
        title.setText(titleStr);
        return view;
    }

    private void initComponents(){
        rec=view.findViewById(R.id.rec);
        title=view.findViewById(R.id.title);
        clear=view.findViewById(R.id.clear);
        acceptBtn=view.findViewById(R.id.acceptBtn);
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
                Products.isMore=true;
                Products.page=1;
                getActivity().finish();
                Products.get().request(1);
            }
        });
    }

    private void setFilters(){
        
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
//        DefaultAdapter adapter=new DefaultAdapter(arrayList,context);
//        rec.setLayoutManager(linearLayoutManager);
//        rec.setAdapter(adapter);
    }

    private void setFonts(){
    }


}