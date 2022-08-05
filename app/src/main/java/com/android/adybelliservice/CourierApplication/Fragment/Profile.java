package com.android.adybelliservice.CourierApplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.adybelliservice.R;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.databinding.CourierFragmentProfileBinding;


public class Profile extends Fragment {

    private CourierFragmentProfileBinding binding;
    private Context context;
    public Profile() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        binding=CourierFragmentProfileBinding.inflate(inflater,container,false);
        binding.fullName.setText(Utils.getSharedPreference(context,"fullname"));
        binding.email.setText(Utils.getSharedPreference(context,"email"));
        return binding.getRoot();
    }
}