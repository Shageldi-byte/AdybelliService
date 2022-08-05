package com.android.adybelliservice.StoreApplication.Fragment.NavbarFragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.adybelliservice.StoreApplication.Adapter.CategoryAdapter.SizeAdapter;
import com.android.adybelliservice.StoreApplication.Fragment.Product.Products;
import com.android.adybelliservice.StoreApplication.Model.Filter.FilterModel.Size;
import com.android.adybelliservice.R;

import java.util.ArrayList;


public class Razmer extends Fragment {

    private Context context;
    private View view;
    public ArrayList<Size> sizes=new ArrayList<>();
    private RecyclerView rec;
    private TextView title;
    private TextView clear;
    private Button acceptBtn;
    private FragmentManager fragmentManager;
    private EditText search;
    private ImageButton searchBtn;
    ArrayList<Size> searchList = new ArrayList<>();
    public Razmer(FragmentManager fragmentManager) {
        this.fragmentManager=fragmentManager;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.store_fragment_razmer, container, false);
        context=getContext();
        initComponents();
        setSizes();
        setListeners();
        setFonts();
        return view;
    }

    private void setSizes(){
        GridLayoutManager layoutManager=new GridLayoutManager(context,4);
        rec.setLayoutManager(layoutManager);
        SizeAdapter adapter=new SizeAdapter(sizes,context);
        rec.setAdapter(adapter);
    }

    private void initComponents(){
        rec=view.findViewById(R.id.rec);
        title=view.findViewById(R.id.title);
        acceptBtn=view.findViewById(R.id.acceptBtn);
        clear=view.findViewById(R.id.clear);
        searchBtn=view.findViewById(R.id.searchBtn);
        search=view.findViewById(R.id.search);
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
                Products.get().request(1);
                getActivity().finish();
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(search.getText().toString().trim().isEmpty()){
                    setSizes();
                    searchList.clear();
                    return;
                }
                searchList.clear();
                for(Size cost:sizes){
                    if(cost.getSize().toLowerCase().contains(search.getText().toString().toLowerCase())){
                        searchList.add(cost);
                    }
                }
                SizeAdapter adapter=new SizeAdapter(searchList,context);
                rec.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setFonts(){
    }
}