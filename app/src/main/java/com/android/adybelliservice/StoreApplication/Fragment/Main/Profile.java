package com.android.adybelliservice.StoreApplication.Fragment.Main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.android.adybelliservice.StoreApplication.Activity.MainActivity;
import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.ConfirmDialog;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.GBody;
import com.android.adybelliservice.StoreApplication.Model.Profile.CountResponse;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.StoreFragmentProfileBinding;
import com.android.adybelliservice.databinding.StoreLangDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wuyr.rippleanimation.RippleAnimation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {

    private Context context;
    private StoreFragmentProfileBinding binding;
    private String firstColor = "";
    private String secondColor = "";

    public Profile() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = StoreFragmentProfileBinding.inflate(getLayoutInflater());
        context = getContext();
        if(Utils.getLanguage(context).equals("ru")){
            binding.langTv.setText(context.getResources().getString(R.string.ru));
        } else {
            binding.langTv.setText(context.getResources().getString(R.string.tm));
        }
        checkMode();
        setListeners();
        return binding.getRoot();
    }

    private void checkMode() {
        if (Utils.getSharedPreference(context, "mode").equals("night")) {
            binding.mode.setChecked(true);
            firstColor = "#FFFFFF";
            secondColor = "#161616";
        } else {
            binding.mode.setChecked(false);
            firstColor = "#161616";
            secondColor = "#FFFFFF";
        }
    }

    private void setListeners() {

        binding.myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.get().getBottomNavigation().setSelectedItemId(R.id.products);
            }
        });
        binding.mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                if (isChecked) {
                    Utils.setPreference("mode", "night", context);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    Utils.setPreference("mode", "light", context);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        binding.lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLangDialog();
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog confirmDialog=new ConfirmDialog(context);
                confirmDialog.setMessage(context.getResources().getString(R.string.logout_message));
                confirmDialog.setTitle(context.getResources().getString(R.string.logout));
                confirmDialog.setOkText(context.getResources().getString(R.string.ok));
                confirmDialog.setCancelText(context.getResources().getString(R.string.cancel));
                confirmDialog.setConfirmDialogListener(new ConfirmDialog.ConfirmDialogListener() {
                    @Override
                    public void onOkPressed() {
                        confirmDialog.setLoading(true);
                        Utils.setPreference("token","",context);
                        getActivity().finish();
                    }

                    @Override
                    public void onCancelPressed() {
                        confirmDialog.dismiss();
                    }
                });
                confirmDialog.show();
            }
        });
    }

    private void showLangDialog(){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);
        StoreLangDialogBinding langDialogBinding=StoreLangDialogBinding.inflate(LayoutInflater.from(context));
        bottomSheetDialog.setContentView(langDialogBinding.getRoot());
        langDialogBinding.tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setLocale("tm",context);
                restart();
            }
        });
        langDialogBinding.ru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setLocale("ru",context);
                restart();
            }
        });
        langDialogBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void restart(){
        try {
            getActivity().recreate();
            getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void getProductsCount(){
        ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<GBody<CountResponse>> call=apiInterface.getSellerProductCount("Bearer "+Utils.getSharedPreference(context,"token"),
                "5c249d738fc4a4ae6f2844c0b744e06a");
        call.enqueue(new Callback<GBody<CountResponse>>() {
            @Override
            public void onResponse(Call<GBody<CountResponse>> call, Response<GBody<CountResponse>> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                    binding.count1.setText(response.body().getBody().getAccepted_products()+"");
                    binding.count2.setText(response.body().getBody().getPending_products()+"");
                    binding.count3.setText(response.body().getBody().getCanceled_products()+"");
                }
            }

            @Override
            public void onFailure(Call<GBody<CountResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            getProductsCount();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getProductsCount();
    }
}