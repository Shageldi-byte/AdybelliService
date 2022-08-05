package com.android.adybelliservice.TrStockApplication.Adapter;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.TrStockApplication.Activity.BoxView;
import com.android.adybelliservice.TrStockApplication.Api.APIClient;
import com.android.adybelliservice.TrStockApplication.Api.ApiInterface;
import com.android.adybelliservice.TrStockApplication.Fragment.BoxDialog;
import com.android.adybelliservice.TrStockApplication.Fragment.BoxDialog2;
import com.android.adybelliservice.TrStockApplication.Model.AddBox;
import com.android.adybelliservice.TrStockApplication.Model.Box;
import com.android.adybelliservice.TrStockApplication.Model.ModifyBoxStatus;
import com.android.adybelliservice.TrStockApplication.Model.SetTruckName;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder> {
    private ArrayList<Box> arrayList = new ArrayList<>();
    private Context context;
    private ViewHolder oldHolder;

    public BoxAdapter(ArrayList<Box> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tr_box_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Box item = arrayList.get(holder.getAbsoluteAdapterPosition());
        holder.name.setText("Name: "+item.getName());
        holder.truck_name.setText("Truck name: "+item.getName_truck());
        holder.id.setText("ID: "+item.getId());
        holder.status.setText("Status: "+item.getStatus());

        holder.change_truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTruckName(item,holder,holder.getAbsoluteAdapterPosition());
            }
        });
        
        holder.change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(item,holder,holder.getAbsoluteAdapterPosition());
            }
        });

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    BoxDialog.selectedBoxId=item.getId();
                    BoxDialog2.selectedBoxId=item.getId();
                    if(oldHolder!=null){
                        oldHolder.check.setChecked(false);
                    }
                    oldHolder=holder;
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BoxView.class).putExtra("id",item.getId()));
            }
        });
    }

    private void changeStatus(Box item, ViewHolder holder, int pos) {
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40,40,40,40);

        alert.setTitle("Change status");

        alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("Biraz garasyn...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ModifyBoxStatus body=new ModifyBoxStatus(item.getId(),"in_truck");
                    ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
                    Call<MyBody<String>> call=apiInterface.modifyBoxStatus("Bearer "+ Utils.getSharedPreference(context,"tkn"),body);
                    call.enqueue(new Callback<MyBody<String>>() {
                        @Override
                        public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                            if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                                item.setStatus("in_truck");
                                arrayList.set(pos,item);
                                notifyItemChanged(pos);
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error code: "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<MyBody<String>> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void setTruckName(Box item, ViewHolder holder,int pos) {
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40,40,40,40);

        final EditText descriptionBox = new EditText(context);
        descriptionBox.setHint("BT3540AH");
        layout.addView(descriptionBox);

        alert.setView(layout);

        alert.setTitle("Change truck");

        alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(descriptionBox.getText().toString().length()>0) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("Biraz garasyn...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    SetTruckName body=new SetTruckName(item.getId(),descriptionBox.getText().toString());
                    ApiInterface apiInterface= APIClient.getClient().create(ApiInterface.class);
                    Call<MyBody<String>> call=apiInterface.setTruckName("Bearer "+ Utils.getSharedPreference(context,"tkn"),body);
                    call.enqueue(new Callback<MyBody<String>>() {
                        @Override
                        public void onResponse(Call<MyBody<String>> call, Response<MyBody<String>> response) {
                            if(response.isSuccessful() && response.body()!=null && response.body().getBody()!=null){
                                item.setName_truck(descriptionBox.getText().toString());
                                arrayList.set(pos,item);
                                notifyItemChanged(pos);
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error code: "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<MyBody<String>> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCheckBox check;
        private TextView id,name,truck_name,status;
        private MaterialButton change_truck,change_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check=itemView.findViewById(R.id.check);
            id=itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            truck_name=itemView.findViewById(R.id.truck_name);
            status=itemView.findViewById(R.id.status);
            change_truck=itemView.findViewById(R.id.change_truck);
            change_status=itemView.findViewById(R.id.change_status);
        }
    }
}
