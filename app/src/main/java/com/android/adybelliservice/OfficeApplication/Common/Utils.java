package com.android.adybelliservice.OfficeApplication.Common;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.adybelliservice.OfficeApplication.Api.APIClient;
import com.android.adybelliservice.OfficeApplication.Api.ApiInterface;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {
    public static void hideAdd(Fragment fragment, String tagFragmentName, FragmentManager mFragmentManager, int frame) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(frame, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    public static void removeShow(Fragment fragment, String tagFragmentName, FragmentManager mFragmentManager, int frame) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(frame, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    public static void setSharedPreference(Context context, String name, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getSharedPreference(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        String value = prefs.getString(name, "");
        return value;
    }

    public static ProgressDialog progressDialog(Context context, String title, String message, Boolean isCancellable) {
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setTitle(title);
        pDialog.setMessage(message);
        pDialog.setCancelable(isCancellable);
        return pDialog;
    }

    public static void updateOrderDetail(String status, String delivery_id, String od_id, Context context){
        ProgressDialog progressDialog=Utils.progressDialog(context,"Biraz garashyn...","Maglumatlar uytgedilyar",false);
        ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
        EditOrder editOrder=new EditOrder(od_id,status,delivery_id);
        Call<MyBody<String>> call=apiInterface.updateOrderDetail(editOrder,"Bearer "+Utils.getSharedPreference(context,"tkn"));
        call.enqueue(new Callback<MyBody<String>>() {
            @Override
            public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Ustunlikli uytgedildi!", Toast.LENGTH_SHORT).show();
                } else{
//                    Utils.refreshToken(context);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyBody<String>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public static void refreshToken(Context context) {
//        ProgressDialog progressDialog = Utils.progressDialog(context, "Please wait...", "refreshing token...", false);
//        progressDialog.show();
//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        Call<LoginResponse> call = apiInterface.refreshToken("Bearer " + Utils.getSharedPreference(context, "refresh_tkn"));
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (response.isSuccessful()) {
//                    Utils.setSharedPreference(context, "tkn", response.body().getAccessToken());
//                    Utils.setSharedPreference(context, "refresh_tkn", response.body().getRefreshToken());
//                } else {
//                    Toast.makeText(context, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//        });
    }

    public static String covertStringToDate(String date) throws ParseException {
        String response = "";
        Date newDate= null;
        if (date != null) {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss");
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            response = spf.format(newDate);
            return response;
        } else{
            return "";
        }
    }
}
