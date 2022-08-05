package com.android.adybelliservice.StoreApplication.Fragment.Orders;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.adybelliservice.StoreApplication.Activity.MainActivity;
import com.android.adybelliservice.StoreApplication.Adapter.Orders.MoreInfoAdapter;
import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.OrderStatus;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.GBody;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserSingleOrder;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserSingleOrderProducts;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserSingleOrderResponse;
import com.android.adybelliservice.StoreApplication.Model.Orders.OrderStatusBody;
import com.android.adybelliservice.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoreInfo extends Fragment {


    private View view;
    private Context context;
    private RecyclerView rec;
    private TextView faktura, orderCodeTitle, orderCode, orderDateTitle, orderDate, orderCountTitle, orderCount, date, count, address, tel, toleg, tt1, t2, t3, t4, t5, t6, t7, t8, totalCostTitle, totalCost, typeCost, back;
    private ArrayList<GetUserSingleOrderProducts> moreInfos = new ArrayList<>();
    private ApiInterface apiInterface;
    private String id;
    private NestedScrollView scroll;
    private ProgressBar progress;
    private LinearLayout con;
    private String customerHtml = "";
    private String addressHtml = "";
    private String productsHtml = "";
    private String pay_method = "";
    private String addressTitle, addressText, phoneText, regionText, cityText;
    private String productsHtmlProduct = "";
    private String total_price, shipping_price;
    private ImageView status;
    private TextView canceled, cancelOrder;
    private static MoreInfo INSTANCE;
    private Button send;
    private int statusPos=0;
    private ProgressDialog progressDialog;

    public static MoreInfo newInstance(String valueOf) {
        Bundle args = new Bundle();
        args.putString("id",valueOf);
        MoreInfo fragment = new MoreInfo();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.store_fragment_more_info, container, false);
        context = getContext();
        this.id= getArguments().getString("id");
        initComponents();
        request();
        INSTANCE=this;
        return view;
    }

    public static MoreInfo get(){
        return INSTANCE;
    }

    public TextView getCancelOrder(){
        return cancelOrder;
    }

    private void request() {
        progress.setVisibility(View.VISIBLE);
        con.setVisibility(View.GONE);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GetUserSingleOrderResponse> call= apiInterface.getUserSingleOrder(id,"5c249d738fc4a4ae6f2844c0b744e06a","Bearer "+Utils.getSharedPreference(context,"token"));
        call.enqueue(new Callback<GetUserSingleOrderResponse>() {
            @Override
            public void onResponse(Call<GetUserSingleOrderResponse> call, Response<GetUserSingleOrderResponse> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                    try {
                        GetUserSingleOrderResponse order = response.body();
                        GetUserSingleOrder body = order.getBody();
                        moreInfos = body.getProducts();
                        orderCode.setText("#" + body.getOrder_id());
                        orderDate.setText(body.getCreated_at());

//                        if (body.getPayment_method() == 1) {
//                            pay_method = Utils.getStringResource(R.string.cash_on_door, context);
//                        }
//                        if (body.getPayment_method() == 2) {
//                            pay_method = Utils.getStringResource(R.string.terminal_on_door, context);
//                        }
//                        if (body.getPayment_method() == 3) {
//                            pay_method = Utils.getStringResource(R.string.online_buy, context);
//                        }
//
//                        Log.e("Status", body.getStatus());

                        if (body.getStatus().equals(OrderStatus.PENDING)) {
                            status.setImageResource(R.drawable.store_ic_baseline_access_time_filled_24);


                        }
                        boolean isCancel=false;
                        for(GetUserSingleOrderProducts more:moreInfos){
                            if(more.getStatus().equals("pending")){
                                isCancel=true;
                                break;
                            }
                        }

                        if(isCancel){
                            send.setVisibility(View.VISIBLE);
                        }

                        if (body.getStatus().equals("accepted") || body.getStatus().equals("in_truck") || body.getStatus().equals("stock_tr") || body.getStatus().equals("stock_tm")) {
                            changeStatus(status,faktura,
                                    R.drawable.store_ic_baseline_check_circle_24,
                                    R.string.pending_status,
                                    R.color.passive_color2);
                        }
                        if (body.getStatus().equals("ontheway") || body.getStatus().equals("packing")) {
                            changeStatus(status,faktura,
                                    R.drawable.store_ic_truck_time,
                                    R.string.on_the_way,
                                    R.color.yellow);
                        }
                        if (body.getStatus().equals("received")) {
                            changeStatus(status,faktura,
                                    R.drawable.store_ic_box_tick,
                                    R.string.received,
                                    R.color.success);
                        }
                        if (body.getStatus().equals("canceled")) {
                            changeStatus(status,faktura,
                                    R.drawable.store_ic_cancel,
                                    R.string.rejeted_status,
                                    R.color.red);
                        }

                        if(body.getStatus().equals("rejected") || body.getStatus().equals("refund")){
                            changeStatus(status,faktura,R.drawable.store_ic_warning,R.string.canceled_status,R.color.red);
                        }


                        String date = body.getCreated_at();
                        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(date);
                            spf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
                            date = spf.format(newDate);
                            orderDate.setText(date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        addressTitle = body.getAddressTitle();
                        addressText = body.getAddress();
                        phoneText = body.getPhone();
                        if (body.getLocation() != null)
                            regionText = body.getLocation().getText();

                        if (body.getSub_location() != null && body.getSub_location().getText() != null)
                            cityText = body.getSub_location().getText();
                        if (Utils.getLanguage(context).equals("ru")) {
                            if (body.getSub_location() != null && body.getSub_location().getText_ru() != null)
                                regionText = body.getLocation().getText_ru();
                            if (body.getSub_location() != null)
                                cityText = body.getSub_location().getText_ru();
                        }

                        orderCount.setText(moreInfos.size() + "");
                        count.setText("#" + body.getAddressTitle());
                        address.setText(body.getAddress());
                        tel.setText(body.getPhone());
                        Double total_price_temp = body.getTotal();
                        Double sum = body.getTotal();
                        shipping_price = 0 + "";
                        if (body.getShipping_price() != null) {
                            shipping_price = body.getShipping_price() + "";
                            total_price_temp = body.getTotal() + body.getShipping_price();
                            sum = body.getTotal() + body.getShipping_price();
                        }
                        total_price = total_price_temp + "";
                        t2.setText(body.getTotal() + " TMT");
                        t4.setText(shipping_price + " TMT");
                        totalCost.setText(sum + " TMT");
                        setOrders();
                        setListener();
                        progress.setVisibility(View.GONE);
                        scroll.setVisibility(View.VISIBLE);

                        con.setVisibility(View.VISIBLE);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<GetUserSingleOrderResponse> call, Throwable t) {

            }
        });

    }

    private void changeStatus(ImageView img,TextView tv, int icon, int status, int color) {
        tv.setText(Utils.getStringResource(status,context));
        tv.setTextColor(context.getResources().getColor(color));
        img.setImageResource(icon);
        img.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }


    private void initComponents() {
        rec = view.findViewById(R.id.rec);
        faktura = view.findViewById(R.id.faktura);
        orderCodeTitle = view.findViewById(R.id.orderCodeTitle);
        orderCode = view.findViewById(R.id.orderCode);
        orderDateTitle = view.findViewById(R.id.orderDateTitle);
        orderDate = view.findViewById(R.id.orderDate);
        orderCountTitle = view.findViewById(R.id.orderCountTitle);
        orderCount = view.findViewById(R.id.orderCount);
        date = view.findViewById(R.id.date);
        count = view.findViewById(R.id.count);
        tel = view.findViewById(R.id.tel);
        toleg = view.findViewById(R.id.toleg);
        tt1 = view.findViewById(R.id.tt1);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);
        t4 = view.findViewById(R.id.t4);
        t5 = view.findViewById(R.id.t5);
        t6 = view.findViewById(R.id.t6);
        t7 = view.findViewById(R.id.t7);
        t8 = view.findViewById(R.id.t8);
        totalCostTitle = view.findViewById(R.id.totalCostTitle);
        totalCost = view.findViewById(R.id.totalCost);
        address = view.findViewById(R.id.address);
        typeCost = view.findViewById(R.id.typeCost);
        back = view.findViewById(R.id.back);
        progress = view.findViewById(R.id.progress);
        scroll = view.findViewById(R.id.scroll);
        status = view.findViewById(R.id.status);
        send = view.findViewById(R.id.send);
        con = view.findViewById(R.id.con);
        send.setTypeface(Utils.getBoldFont(context));
    }

    private void setOrders() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        MoreInfoAdapter adapter = new MoreInfoAdapter(moreInfos, context);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(adapter);
    }

    private void setListener() {
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backTo();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(context);
                progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.show();
                updateOrderStatus();
            }
        });

    }

    private void updateOrderStatus(){
        apiInterface=APIClient.getClient().create(ApiInterface.class);
        OrderStatusBody orderStatusBody=new OrderStatusBody(moreInfos.get(statusPos).getOd_id()+"",moreInfos.get(statusPos).getStatus());
        Call<GBody<String>> call= apiInterface.setOrderProductStatus("Bearer "+Utils.getSharedPreference(context,"token"),"5c249d738fc4a4ae6f2844c0b744e06a",orderStatusBody);
        call.enqueue(new Callback<GBody<String>>() {
            @Override
            public void onResponse(Call<GBody<String>> call, Response<GBody<String>> response) {
                if(response.isSuccessful()){

                } else {

                }
                if(statusPos<moreInfos.size()) {
                    statusPos++;
                    updateOrderStatus();
                } else {
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GBody<String>> call, Throwable t) {

            }
        });
    }

    private void backTo() {
        Utils.removeShow(new Orders(), Orders.class.getSimpleName(), getFragmentManager(), R.id.content);
        MainActivity.thirdFragment = new Orders();
    }










}