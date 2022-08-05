package com.android.adybelliservice.StoreApplication.Fragment.Main;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.adybelliservice.StoreApplication.Adapter.ViewPager.ProductPagerAdapter;
import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.ProductStatus;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOption;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOptionBody;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOptionPost;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreFragmentProductsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Products extends Fragment {

    private Context context;
    private StoreFragmentProductsBinding binding;

    public Products() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = StoreFragmentProductsBinding.inflate(inflater, container, false);
        context = getContext();
        createTabs();
        setBottomNavigationItems(context,binding.viewpagertab);
        setFonts();
        return binding.getRoot();
    }

    private void setFonts() {
    }

    private void createTabs() {
        View child = binding.viewpager.getChildAt(0);
        if (child instanceof RecyclerView) {
            child.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        ArrayList<String> texts=new ArrayList<>();
        texts.add(context.getResources().getString(R.string.accepted));
        texts.add(context.getResources().getString(R.string.pending));
        texts.add(context.getResources().getString(R.string.canceled));
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(com.android.adybelliservice.StoreApplication.Fragment.Product.Products.newInstance(ProductStatus.ACCEPT));
        fragments.add(com.android.adybelliservice.StoreApplication.Fragment.Product.Products2.newInstance(ProductStatus.PENDING));
        fragments.add(com.android.adybelliservice.StoreApplication.Fragment.Product.Products2.newInstance(ProductStatus.CANCELED));
        ProductPagerAdapter adapter = new ProductPagerAdapter(getActivity(), fragments);
        binding.viewpager.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.viewpagertab, binding.viewpager,(tab, position) -> tab.setText(texts.get(position)));
        tabLayoutMediator.attach();
    }

    public void setBottomNavigationItems(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setBottomNavigationItems(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Utils.getMediumFont(context));

            }
        } catch (Exception e) {
        }
    }





}