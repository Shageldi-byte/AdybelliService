package com.android.adybelliservice.StoreApplication.Adapter.AddProduct;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Model.AddProduct.AdditionalInformation;
import com.android.adybelliservice.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AdditionalInformationAdapter extends RecyclerView.Adapter<AdditionalInformationAdapter.ViewHolder> {
    private ArrayList<AdditionalInformation> arrayList=new ArrayList<>();
    private Context context;

    public AdditionalInformationAdapter(ArrayList<AdditionalInformation> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.store_additional_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        AdditionalInformation item=arrayList.get(holder.getAdapterPosition());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()>1) {
                    arrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            }
        });

        holder.title.setText(item.getText());
        holder.value.setText(item.getText_ru());

        holder.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayList.get(holder.getAdapterPosition()).setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayList.get(holder.getAdapterPosition()).setText_ru(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputLayout titleCon,valueCon;
        private TextInputEditText title,value;
        private ImageView remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remove=itemView.findViewById(R.id.remove);
            titleCon=itemView.findViewById(R.id.titleCon);
            valueCon=itemView.findViewById(R.id.valueCon);
            title=itemView.findViewById(R.id.title);
            value=itemView.findViewById(R.id.value);
        }
    }
}
