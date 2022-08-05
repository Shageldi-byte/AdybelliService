package com.android.adybelliservice.CourierApplication.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.CourierApplication.Adapter.AcceptedAdapter;
import com.android.adybelliservice.CourierApplication.Model.Order;
import com.android.adybelliservice.CourierApplication.Model.Status;
import com.android.adybelliservice.R;

import java.util.ArrayList;


public class Accepted extends Fragment {

    private LinearLayout items;
    private View view;
    private Context context;
    private ArrayList<Status> statuses=new ArrayList<>();
    private ArrayList<Order> orders=new ArrayList<>();
    private TextView oldTv=null;
    private RelativeLayout oldCard=null;
    private RecyclerView rec;
    public Accepted() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.courier_fragment_accepted, container, false);
        context=getContext();
        initComponents();
        setStatusOrders("ready");
        setStatus();
        return view;
    }

    private void setOrders() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        AcceptedAdapter adapter=new AcceptedAdapter(context,orders);
        rec.setAdapter(adapter);
        rec.setLayoutManager(linearLayoutManager);
    }

    private void setStatus() {
        statuses.clear();
        statuses.add(new Status("Taýýar","ready",1));
        statuses.add(new Status("Ýolda","on_the_way",2));
        statuses.add(new Status("Eltip berilen","delivered",3));
        statuses.add(new Status("Tamamlandy","completed",4));

        items.removeAllViews();
        int k=0;

        for(Status status:statuses){
            View view=LayoutInflater.from(context).inflate(R.layout.courier_status_item, null, false);
            TextView tv=view.findViewById(R.id.value);
            RelativeLayout card=view.findViewById(R.id.card);
            if(k==0){
                setStatusActive(tv,card);
                oldTv=tv;
                oldCard=card;
            }
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(oldTv!=null){
                        setStatusPassive(oldTv,oldCard);
                    }
                    setStatusActive(tv,card);
                    oldTv=tv;
                    oldCard=card;
                    setStatusOrders(status.getStatus());
                }
            });
            tv.setText(status.getName());
            items.addView(view);
            k++;
        }
    }

    private void setStatusOrders(String status) {
        orders.clear();
        for(int i=0;i<=30;i++){
            orders.add(new Order("",status));
        }
        setOrders();
    }

    private void setStatusActive(TextView tv, RelativeLayout card) {
        tv.setTextColor(Color.WHITE);
        card.setBackgroundResource(R.drawable.courier_primary_shadow_bg);
    }
    private void setStatusPassive(TextView tv, RelativeLayout card) {
        tv.setTextColor(Color.BLACK);
        card.setBackgroundResource(R.drawable.courier_shadow_bg);
    }

    private void initComponents() {
        items=view.findViewById(R.id.items);
        rec=view.findViewById(R.id.rec);
    }
}