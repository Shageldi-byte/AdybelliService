package com.android.adybelliservice.OfficeApplication.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.adybelliservice.OfficeApplication.Model.Tir;
import com.android.adybelliservice.R;

import java.util.ArrayList;

public class Custom_Spinner extends ArrayAdapter {
    public Custom_Spinner(@NonNull Context context, ArrayList<Tir> customlist) {
        super(context, 0, customlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.ofis_custom_spinner,parent,false);

        }
        Tir item= (Tir) getItem(position);

        TextView spinnerTv=convertView.findViewById(R.id.tvSpinnerLayout);
        if (item!=null) {

            spinnerTv.setText(item.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.ofis_custom_dropdown_layout,parent,false);

        }
        Tir item= (Tir) getItem(position);

        TextView dropDownTV=convertView.findViewById(R.id.tvDropDownLayout);
        if (item!=null) {

            dropDownTV.setText(item.getName());
        }
        return convertView;
    }
}
