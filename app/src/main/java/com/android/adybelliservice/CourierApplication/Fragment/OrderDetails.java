package com.android.adybelliservice.CourierApplication.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.adybelliservice.CourierApplication.Adapter.InTruckAdapter;
import com.android.adybelliservice.CourierApplication.Api.APIClient;
import com.android.adybelliservice.CourierApplication.Api.ApiInterface;
import com.android.adybelliservice.CourierApplication.Common.Utils;
import com.android.adybelliservice.CourierApplication.Database.BoxDB;
import com.android.adybelliservice.CourierApplication.Model.Courier;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.BoxToOds;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderDetailResponse;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderProductColor;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderProductInfo;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetails extends Fragment {

    private View view;
    private Context context;
    private ArrayList<OrderProduct> arrayList = new ArrayList<>();
    private RecyclerView rec;
    private ArrayList<Courier> couriers = new ArrayList<>();
    private String status = "stock_tm";
    private ProgressBar progress;
    private boolean isLoading = false;
    private Integer count = 1;
    private boolean shouldLoadMore = true;
    private SwipeRefreshLayout swipeRefresh;
    private TextView title;
    private EditText search;
    private BoxDB boxDB;
    public OrderDetails(String status) {
        this.status = status;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.courier_fragment_order_details, container, false);
        context = getContext();
        boxDB=new BoxDB(context);
        initComponents();
        if (status.equals("ontheway")) {
            title.setText("On delivery");
        } else if (status.equals("rejected")) {
            title.setText("Canceled");
        }
        setListener();
        if (Utils.getSharedPreference(context,"isInternet").equals("yes")) {
            request(1,search.getText().toString());
        } else {
            offlineMode(search.getText().toString());
        }

        return view;
    }

    private void offlineMode(String s) {
        progress.setVisibility(View.GONE);
        if(s.isEmpty()){
            Cursor cursor=boxDB.getSelect(status);
            arrayList.clear();
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    OrderProductInfo info = new OrderProductInfo("",new OrderProductColor("","","",""));
                    Integer d_id=null;
                    try{
                        d_id=Integer.valueOf(cursor.getString(8));
                    } catch (Exception exception){
                        exception.printStackTrace();
                        d_id=-1;
                    }
                    arrayList.add(new OrderProduct(
                            cursor.getString(9),
                            cursor.getString(1),
                            cursor.getString(4),
                            Integer.parseInt(cursor.getString(5)),
                            Double.parseDouble(cursor.getString(3)),
                            cursor.getString(7),
                            cursor.getString(2),
                            cursor.getString(2),
                            0,
                            "",
                            0.0,
                            0.0,
                            0.0,
                            0.0,
                            cursor.getString(6),
                            "",
                            "",
                            "",
                            d_id,
                            info,
                            new ArrayList<BoxToOds>()
                    ));
                }

            }
            setRecycler();
        } else {
            Cursor cursor=boxDB.getSearch(status,s);
            arrayList.clear();
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    Integer d_id=null;
                    try{
                        d_id=Integer.valueOf(cursor.getString(8));
                    } catch (Exception exception){
                        exception.printStackTrace();
                        d_id=-1;
                    }
                    OrderProductInfo info = new OrderProductInfo("",new OrderProductColor("","","",""));
                    arrayList.add(new OrderProduct(
                            cursor.getString(9),
                            cursor.getString(1),
                            cursor.getString(4),
                            Integer.parseInt(cursor.getString(5)),
                            Double.parseDouble(cursor.getString(3)),
                            cursor.getString(7),
                            cursor.getString(2),
                            cursor.getString(2),
                            0,
                            "",
                            0.0,
                            0.0,
                            0.0,
                            0.0,
                            cursor.getString(6),
                            "",
                            "",
                            "",
                            d_id,
                            info,
                            new ArrayList<BoxToOds>()
                    ));
                }


            }
            setRecycler();
        }
    }

    private void setListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.getSharedPreference(context,"isInternet").equals("yes")) {
                    count = 1;
                    shouldLoadMore = true;
                    request(1,search.getText().toString());
                } else {
                    offlineMode(search.getText().toString());
                    swipeRefresh.setRefreshing(false);
                }


            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    if (Utils.getSharedPreference(context,"isInternet").equals("yes")) {
                        count = 1;
                        shouldLoadMore = true;
                        request(1,search.getText().toString());
                    } else {
                        offlineMode(search.getText().toString());
                    }

                    return true;
                }
                return false;
            }
        });

        rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading && Utils.getSharedPreference(context,"isInternet").equals("yes"))
                        loadMore();

                }
            }
        });
    }

    private void loadMore() {
        isLoading = true;
        try {
            progress.setVisibility(View.VISIBLE);
            count = count + 1;
            request(count,search.getText().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            isLoading = false;
        }

    }

    private void request(int i,String order_id) {
        if (!shouldLoadMore) {
            setDefault();
            count = count - 1;
            return;
        }
        if (i == 1) {
            arrayList.clear();
        }
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Map<String, Object> params = new HashMap<>();
        params.put("per_page", 20);
        params.put("status", status);
        params.put("delivery_id", Utils.getSharedPreference(context,"id"));
        params.put("page", i);
        if(!order_id.isEmpty())
            params.put("order_id",order_id);
        Call<MyBody<ArrayList<OrderProduct>>> call = apiInterface.getOrderDetailsForCourier(params, "Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<MyBody<ArrayList<OrderProduct>>>() {
            @Override
            public void onResponse(Call<MyBody<ArrayList<OrderProduct>>> call, Response<MyBody<ArrayList<OrderProduct>>> response) {
                if (response.isSuccessful()) {
                    boxDB.deleteByStatus(status);
                    arrayList.addAll(response.body().getBody());
                    for(OrderProduct product:response.body().getBody()){
                        boxDB.insert(product.getOrder_id(),
                                product.getName(),
                                product.getPrice()+"",
                                product.getSize(),
                                product.getCount()+"",
                                product.getStatus(),
                                product.getImage(),
                                product.getDelivery_id()+"",
                                product.getOd_id());
                    }
                    setDefault();
                    if (response.body().getBody().size() <= 0) {
                        shouldLoadMore = false;
                    }
                    if (i == 1) {
                        setRecycler();
                    } else {
                        rec.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    setDefault();
                    Utils.refreshToken(context);
                }
            }

            @Override
            public void onFailure(Call<MyBody<ArrayList<OrderProduct>>> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                setDefault();
            }
        });
    }

    private void setDefault() {
//        list.setRefreshing(true);
        swipeRefresh.setRefreshing(false);
        progress.setVisibility(View.GONE);
        isLoading = false;
    }

    private void setRecycler() {
        rec.setAdapter(new InTruckAdapter(context, arrayList,status));
        rec.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initComponents() {
        rec = view.findViewById(R.id.rec);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        progress = view.findViewById(R.id.progress);
        title = view.findViewById(R.id.title);
        search = view.findViewById(R.id.search);
    }


}