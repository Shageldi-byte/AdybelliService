package com.android.adybelliservice.OfficeApplication.Activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Adapter.ProductsAdapter;
import com.android.adybelliservice.OfficeApplication.Model.Courier;
import com.android.adybelliservice.OfficeApplication.Model.Products;
import com.android.adybelliservice.R;

import java.util.ArrayList;

public class SetCourier extends AppCompatActivity {
    private ArrayList<Products> arrayList=new ArrayList<>();
    private Context context=this;
    private RecyclerView rec;
    private ArrayList<Courier> couriers=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ofis_activity_set_courier);
        initComponents();
        setRecyclerView();
    }

    private void setRecyclerView() {


        arrayList.add(new Products(1,"Spor ayakgabi","https://assets.ajio.com/medias/sys_master/root/hc4/h09/13018715553822/-288Wx360H-460342492-blue-MODEL.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","http://cdn.shopify.com/s/files/1/0107/9820/2938/products/gmmobile1_grande.png?v=1629754332","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy,c_fill,g_auto/e725107a3d7041389f94ab220123fbcb_9366/Bravada_Shoes_Black_FV8085_01_standard.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://cdn.shopclues.com/images1/thumbnails/108132/320/320/150200694-108132711-1593058388.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://cdn.shopclues.com/images1/thumbnails/104158/320/320/148648730-104158193-1592481791.jpg","US.POLO","550","48"));
        arrayList.add(new Products(1,"Spor ayakgabi","https://assetscdn1.paytm.com/images/catalog/product/F/FO/FOOSMOKY-TRENDYSMOK381955669A9D8/1622965634045_0..jpg","US.POLO","550","48"));
        rec.setAdapter(new ProductsAdapter(arrayList,context,couriers));
        rec.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initComponents() {
        rec=findViewById(R.id.rec);
    }
}