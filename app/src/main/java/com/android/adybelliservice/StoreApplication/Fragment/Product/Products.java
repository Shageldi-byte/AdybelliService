package com.android.adybelliservice.StoreApplication.Fragment.Product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.android.adybelliservice.StoreApplication.Activity.FilterActivity;
import com.android.adybelliservice.StoreApplication.Adapter.Product.ProductAdapter;
import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.ConfirmDialog;
import com.android.adybelliservice.StoreApplication.Common.LayoutType;
import com.android.adybelliservice.StoreApplication.Common.ProductStatus;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOption;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOptionBody;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOptionPost;
import com.android.adybelliservice.StoreApplication.Model.GBody;
import com.android.adybelliservice.StoreApplication.Model.Product.GetAcceptedProductBody;
import com.android.adybelliservice.StoreApplication.Model.Product.GetProductBody;
import com.android.adybelliservice.StoreApplication.Model.Product.Product;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreFragmentProducts2Binding;
import com.android.adybelliservice.databinding.StoreSortDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Products extends Fragment {

    private StoreFragmentProducts2Binding binding;
    private Context context;
    private String status;
    private ArrayList<Product> products = new ArrayList<>();
    private ApiInterface apiInterface;
    public static Integer page = 1;
    private Integer limit = 20;
    private LinearLayout empty_container;
    public static boolean isLoading = false, isMore = true;

    public static ArrayList<String> categoriesStr = new ArrayList<>();
    public static ArrayList<String> brandsStr = new ArrayList<>();
    public static ArrayList<String> sizesStr = new ArrayList<>();
    public static ArrayList<String> colorsStr = new ArrayList<>();
    public static Integer isDiscount = 0;
    public static Double min, max;

    private Integer sort = 0;
    private ProductOptionBody productOptionBody;
    private static Products INSTANCE;


    public Products() {
    }

    public static Products newInstance(String status) {
        Bundle args = new Bundle();
        args.putString("status", status);
        Products f = new Products();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = getArguments().getString("status");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = StoreFragmentProducts2Binding.inflate(inflater, container, false);
        context = getContext();
        INSTANCE=this;
        empty_container = binding.getRoot().findViewById(R.id.empty_container);
        setFonts();
        page=1;
        isMore=true;
        request(page);
        setListener();
        if (status.equals(ProductStatus.ACCEPT)) {
            binding.appBarLayout.setVisibility(View.VISIBLE);
        } else {
            binding.appBarLayout.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }

    public static Products get(){
        return INSTANCE;
    }

    private void requestFilter() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        String strCat = Arrays.toString(categoriesStr.toArray());
        String strBrand = Arrays.toString(brandsStr.toArray());
        String strSize = Arrays.toString(sizesStr.toArray());
        String strColor = Arrays.toString(colorsStr.toArray());
        ProductOptionPost post = new ProductOptionPost(strBrand.substring(1, strBrand.length() - 1), strCat.substring(1, strCat.length() - 1),
                strSize.substring(1, strSize.length() - 1), strColor.substring(1, strColor.length() - 1), isDiscount);
        Call<ProductOption> call = apiInterface.getProductsOptions(post, "5c249d738fc4a4ae6f2844c0b744e06a","Bearer "+Utils.getSharedPreference(context,"token"));
        call.enqueue(new Callback<ProductOption>() {
            @Override
            public void onResponse(Call<ProductOption> call, Response<ProductOption> response) {
                if (response.isSuccessful()) {
                    ProductOption productOption = response.body();
                    productOptionBody = productOption.getBody();
                    Log.e("Filter",productOption.getBody().getColors().size()+"");
                    binding.filter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FilterActivity.productOptionBody=productOptionBody;
                            Intent intent = new Intent(context, FilterActivity.class);
                            context.startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ProductOption> call, Throwable t) {

            }
        });
    }

    public void request(int p) {
        empty_container.setVisibility(View.GONE);
        if (p == 1) {
            showLoading();
            products.clear();
            isMore = true;
        }
        isLoading = true;
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<GBody<ArrayList<Product>>> call = apiInterface.getProducts("5c249d738fc4a4ae6f2844c0b744e06a", "Bearer " + Utils.getSharedPreference(context, "token"),
                new GetProductBody(p, limit, status));
        if (status.equals(ProductStatus.ACCEPT)) {
            String strCat = Arrays.toString(categoriesStr.toArray());
            String strBrand = Arrays.toString(brandsStr.toArray());
            String strSize = Arrays.toString(sizesStr.toArray());
            String strColor = Arrays.toString(colorsStr.toArray());
            GetAcceptedProductBody bodyAccept = new GetAcceptedProductBody(
                    strBrand.substring(1, strBrand.length() - 1),
                    strCat.substring(1, strCat.length() - 1),
                    strSize.substring(1, strSize.length() - 1),
                    strColor.substring(1, strColor.length() - 1),
                    isDiscount,
                    p,
                    limit,
                    sort,
                    min,
                    max,
                    Utils.getSharedPreference(context, "user_id"));
            call = apiInterface.getAcceptedProducts("5c249d738fc4a4ae6f2844c0b744e06a",
                    "Bearer " + Utils.getSharedPreference(context, "token"),
                    bodyAccept);
        }
        call.enqueue(new Callback<GBody<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<GBody<ArrayList<Product>>> call, Response<GBody<ArrayList<Product>>> response) {
                try {
                    if (products != null && products.size() > 0 && products.get(products.size() - 1).getLayout_type().equals(LayoutType.PAGINATION)) {
                        products.remove(products.size() - 1);
                        try {
                            binding.productsAdapter.getAdapter().notifyDataSetChanged();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            setProducts();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (response.isSuccessful()) {
                    if (response.body().getBody() != null && response.body().getBody().size() > 0) {
                        binding.contaoner.setVisibility(View.VISIBLE);
                        binding.productsAdapter.setVisibility(View.VISIBLE);
                        empty_container.setVisibility(View.GONE);
                        if (p == 1) {
                            products = response.body().getBody();
                            setProducts();
                        } else {
                            products.addAll(response.body().getBody());
                            try {
                                binding.productsAdapter.getAdapter().notifyDataSetChanged();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                setProducts();
                            }
                        }
                    } else {
                        if (p == 1) {
                            empty_container.setVisibility(View.VISIBLE);
                            binding.contaoner.setVisibility(View.VISIBLE);
                            if(products.size()<=0){
                                binding.productsAdapter.setVisibility(View.GONE);
                            }
                        }
                        isMore = false;
                    }
                } else {
                    if (products == null || products.size() <= 0) {
                        empty_container.setVisibility(View.VISIBLE);
                        binding.contaoner.setVisibility(View.VISIBLE);
                        if(products.size()<=0){
                            binding.productsAdapter.setVisibility(View.GONE);
                        }
                    }
                    page -= 1;

                }

                binding.loading.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);
                isLoading = false;
                Log.e("Code",response.code()+"");
                if(p==1){
                    requestFilter();
                }
            }

            @Override
            public void onFailure(Call<GBody<ArrayList<Product>>> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                isLoading = false;
                binding.swipeRefresh.setRefreshing(false);
                binding.contaoner.setVisibility(View.VISIBLE);
                if(products.size()<=0){
                    binding.productsAdapter.setVisibility(View.GONE);
                }
                page -= 1;
                requestFilter();
                Log.e("Error",t.getMessage());
            }
        });
    }

    private void setListener() {
        binding.sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        binding.productsAdapter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!binding.productsAdapter.canScrollVertically(1)) {
                    if (!isLoading && isMore) {
                        page++;
                        request(page);
                    }
                }
            }
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                request(page);
            }
        });
    }

    private void setProducts() {
        if (isMore && products.size() >= limit)
            addPagination();
        binding.productsAdapter.setAdapter(new ProductAdapter(context, products, status));
        binding.productsAdapter.setLayoutManager(new LinearLayoutManager(context));

    }

    private void addPagination() {
        products.add(new Product(-1,
                "",
                "",
                0.0,
                0.0,
                "",
                1,
                "",
                status,
                "",
                "",
                LayoutType.PAGINATION,
                false,
                false
                ));
    }

    private void setFonts() {
        binding.sort.setTypeface(Utils.getRegularFont(context));
        binding.filter.setTypeface(Utils.getRegularFont(context));
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        StoreSortDialogBinding sortDialogBinding = StoreSortDialogBinding.inflate(LayoutInflater.from(context));
        bottomSheetDialog.setContentView(sortDialogBinding.getRoot());
        setSortButtonListener(sortDialogBinding.firstSort, sortDialogBinding.priceAsc, sortDialogBinding,0);
        setSortButtonListener(sortDialogBinding.secondSort, sortDialogBinding.priceDesc, sortDialogBinding,1);
        setSortButtonListener(sortDialogBinding.thirdSort, sortDialogBinding.newSort, sortDialogBinding,2);
        bottomSheetDialog.show();
    }

    private void setSortButtonListener(LinearLayout button, RadioButton checked, StoreSortDialogBinding sortDialogBinding,int s) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uncheckSortButtons(sortDialogBinding);
                checked.setChecked(true);
                isMore=true;
                page=1;
                sort=s;
                request(page);
            }
        });

        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uncheckSortButtons(sortDialogBinding);
                checked.setChecked(true);
                isMore=true;
                page=1;
                sort=s;
                request(page);
            }
        });
    }

    private void uncheckSortButtons(StoreSortDialogBinding sortDialogBinding) {
        sortDialogBinding.priceAsc.setChecked(false);
        sortDialogBinding.priceDesc.setChecked(false);
        sortDialogBinding.newSort.setChecked(false);
    }

    private void showLoading() {
        binding.loading.setVisibility(View.VISIBLE);
        binding.contaoner.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}