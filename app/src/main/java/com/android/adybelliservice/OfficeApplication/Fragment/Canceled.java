package com.android.adybelliservice.OfficeApplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.adybelliservice.OfficeApplication.Adapter.DeliveredCanceledAdapter;
import com.android.adybelliservice.OfficeApplication.Model.Products;
import com.android.adybelliservice.R;

import java.util.ArrayList;


public class Canceled extends Fragment {

    private View view;
    private Context context;
    private RecyclerView rec;
    private ArrayList<Products> arrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;

    public Canceled() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ofis_fragment_canceled, container, false);
        context = getContext();
        initComponents();
        setRecycler();
        setListener();
        return view;
    }

    private void setListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void setRecycler() {
        arrayList.add(new Products(1, "Spor ayakgabi", "https://hips.hearstapps.com/vader-prod.s3.amazonaws.com/1610416577-vans-1610416571.jpg", "US.POLO", "550", "48"));
        arrayList.add(new Products(1, "Spor ayakgabi", "https://i1.adis.ws/i/tom_ford/J1100T-TOF001_U4006_APPENDGRID?$listing_grid$", "US.POLO", "550", "48"));
        arrayList.add(new Products(1, "Spor ayakgabi", "https://images.asos-media.com/products/nike-air-force-107-in-white/23916587-1-white?$n_480w$&wid=476&fit=constrain", "US.POLO", "550", "48"));
        arrayList.add(new Products(1, "Spor ayakgabi", "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy,c_fill,g_auto/d641f18ab0144d80b6a9abb10116a6ce_9366/Breaknet_Shoes_Black_FX8708_01_standard.jpg", "US.POLO", "550", "48"));
        arrayList.add(new Products(1, "Spor ayakgabi", "https://rukminim1.flixcart.com/image/332/398/kcc9q4w0/shoe/v/y/b/springw-7-luxury-fashion-white-original-imafthnqp4jyw3gp.jpeg?q=50", "US.POLO", "550", "48"));
        arrayList.add(new Products(1, "Spor ayakgabi", "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/192661/02/sv01/fnd/IND/fmt/png/HYBRID-Fuego-Men's-Running-Shoes", "US.POLO", "550", "48"));
        rec.setAdapter(new DeliveredCanceledAdapter(arrayList, context));
        rec.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initComponents() {
        rec = view.findViewById(R.id.rec);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
    }
}