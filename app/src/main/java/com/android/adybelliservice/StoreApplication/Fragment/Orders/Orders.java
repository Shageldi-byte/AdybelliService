package com.android.adybelliservice.StoreApplication.Fragment.Orders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Activity.MainActivity;
import com.android.adybelliservice.StoreApplication.Adapter.Orders.OrderAdapter;
import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.OrderStatus;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserOrderProducts;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserOrdersResponse;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserOrdersResponseBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreFragmentOrdersBinding;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.github.yasevich.endlessrecyclerview.EndlessRecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Orders extends Fragment implements EndlessRecyclerView.Pager {


    private View view;
    private Context context;
    private ArrayList<GetUserOrdersResponseBody> orders = new ArrayList<>();
    private TextView back;
    private ApiInterface apiInterface;
    public int count = 1;
    private boolean loading = true;
    private SkeletonScreen skeletonScreen;
    private ProgressBar progress;
    private OrderAdapter adapter;
    private EndlessRecyclerView list;
    private boolean shouldLoadMore = true;
    private int limit = 20;
    private StoreFragmentOrdersBinding binding;
    private LinearLayout empty_container;

    public Orders() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = StoreFragmentOrdersBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        context = getContext();
        initComponents();
        setListener();
        request(1);

        return binding.getRoot();
    }


    public void request(int page) {
        if (page == 1) {
            binding.loading.setVisibility(View.VISIBLE);
            binding.progress.setVisibility(View.GONE);
        } else {
            binding.loading.setVisibility(View.GONE);
            if(orders.size()>=limit)
                binding.progress.setVisibility(View.VISIBLE);
        }

        loading=false;

        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GetUserOrdersResponse> call = apiInterface.getUserOrders(page, limit, "5c249d738fc4a4ae6f2844c0b744e06a", "Bearer " + Utils.getSharedPreference(context, "token"));
        call.enqueue(new Callback<GetUserOrdersResponse>() {
            @Override
            public void onResponse(Call<GetUserOrdersResponse> call, Response<GetUserOrdersResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getBody() != null && response.body().getBody().size() > 0) {
                        orders.addAll(response.body().getBody());
                        if (page == 1) {
                            setOrders();
                        } else {
                            try{
                                binding.list.getAdapter().notifyDataSetChanged();
                            } catch (Exception ex){
                                ex.printStackTrace();
                                setOrders();
                            }
                        }
                    } else {
                        shouldLoadMore=false;
                    }


                } else {
                    Log.e("Error",response.code()+"");
                    count--;
                }

                if (orders.size() <= 0) {
                    empty_container.setVisibility(View.VISIBLE);

                }
                binding.loading.setVisibility(View.GONE);
                binding.progress.setVisibility(View.GONE);
                loading=true;
            }

            @Override
            public void onFailure(Call<GetUserOrdersResponse> call, Throwable t) {
                Log.e("Error",t.getMessage());
                binding.loading.setVisibility(View.GONE);
                binding.progress.setVisibility(View.GONE);
                if (orders.size() <= 0) {
                    empty_container.setVisibility(View.VISIBLE);
                }
                loading=true;
                count--;
            }
        });


    }

    private void initComponents() {
        list = view.findViewById(R.id.list);
        back = view.findViewById(R.id.back);
        empty_container = view.findViewById(R.id.empty_container);
        progress = view.findViewById(R.id.progress);
        back.setTypeface(Utils.getBoldFont(context));
    }

    private void setOrders() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        adapter = new OrderAdapter(orders, context, getParentFragmentManager());
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(adapter);
        list.setPager(this);
    }

    private void setListener() {
    }


    private void setDefault() {
        list.setRefreshing(false);
        progress.setVisibility(View.GONE);
        shouldLoadMore = true;
    }

    @Override
    public boolean shouldLoad() {
        return shouldLoadMore;
    }

    @Override
    public void loadNextPage() {
        loadMore();
    }

    private void loadMore() {
        if (loading) {
            count++;
            request(count);
        }
    }
}