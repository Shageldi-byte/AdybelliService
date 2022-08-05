package com.android.adybelliservice.OfficeApplication.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.adybelliservice.OfficeApplication.Fragment.Canceled;
import com.android.adybelliservice.OfficeApplication.Fragment.Delivered;
import com.android.adybelliservice.OfficeApplication.Fragment.InStock;
import com.android.adybelliservice.OfficeApplication.Fragment.InStockCourier;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                InStock inStock = new InStock();
                return inStock;
            case 1:
                InStockCourier inStockCourier = new InStockCourier("");
                return inStockCourier;
            case 2:
                Delivered delivered = new Delivered();
                return delivered;
            case 3:
                Canceled canceled = new Canceled();
                return canceled;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
