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


public class Delivered extends Fragment {


    private View view;
    private Context context;
    private RecyclerView rec;
    private ArrayList<Products> arrayList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    public Delivered() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ofis_fragment_delivered, container, false);
        context=getContext();
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
        arrayList.clear();
        arrayList.add(new Products(1,"Spor ayakgabi","https://assets.ajio.com/medias/sys_master/root/hc4/h09/13018715553822/-288Wx360H-460342492-blue-MODEL.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","http://cdn.shopify.com/s/files/1/0107/9820/2938/products/gmmobile1_grande.png?v=1629754332","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy,c_fill,g_auto/e725107a3d7041389f94ab220123fbcb_9366/Bravada_Shoes_Black_FV8085_01_standard.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://cdn.shopclues.com/images1/thumbnails/108132/320/320/150200694-108132711-1593058388.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://cdn.shopclues.com/images1/thumbnails/104158/320/320/148648730-104158193-1592481791.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://assetscdn1.paytm.com/images/catalog/product/F/FO/FOOSMOKY-TRENDYSMOK381955669A9D8/1622965634045_0..jpg","US.POLO","550","48"));
        rec.setAdapter(new DeliveredCanceledAdapter(arrayList,context));
        rec.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initComponents() {
        rec=view.findViewById(R.id.rec);
        swipeRefresh=view.findViewById(R.id.swipeRefresh);
    }
}