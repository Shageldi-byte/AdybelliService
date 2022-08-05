package com.android.adybelliservice.OfficeApplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.OfficeApplication.Activity.SeeProducts;
import com.android.adybelliservice.OfficeApplication.Common.Utils;
import com.android.adybelliservice.OfficeApplication.Model.InStockModel;
import com.android.adybelliservice.R;
import com.google.android.material.snackbar.Snackbar;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class InStockAdapter extends RecyclerView.Adapter<InStockAdapter.ViewHolder> {
    private ArrayList<InStockModel> arrayList = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;

    public InStockAdapter(ArrayList<InStockModel> arrayList, Context context, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ofis_acordion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InStockAdapter.ViewHolder holder, int position) {
        InStockModel object = arrayList.get(position);
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.container.getVisibility() == View.VISIBLE) {
                    holder.container.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.ofis_ic_baseline_keyboard_arrow_right_24);
                } else {
                    holder.container.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ofis_ic_baseline_keyboard_arrow_down_24);
                }
            }
        });

        holder.boxCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.container.getVisibility() == View.VISIBLE) {
                    holder.container.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.ofis_ic_baseline_keyboard_arrow_right_24);
                } else {
                    holder.container.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ofis_ic_baseline_keyboard_arrow_down_24);
                }
            }
        });

        holder.boxCode.setText("Box ady: "+object.getName()+" / ID: "+object.getId());
        holder.status.setText("Status: "+object.getStatus());
        try {
            holder.cargo_date.setText("Doredilen senesi: "+ Utils.covertStringToDate(object.getCreated_at()));
//            holder.box_info.setText("Uytgedilen senesi: "+ Utils.covertStringToDate(object.getUpdatedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.truck.setText("Truck ady: "+object.getName_truck());
        holder.statusDetail.setText("Haryt sany: "+object.getProd_count());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SeeProducts.class);
                intent.putExtra("id",object.getId()+"");
                context.startActivity(intent);
            }
        });


    }

    private void showDateTimePicker() {
        // Initialize
        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Wagt we sene saýla",
                "OK",
                "Cancel"
        );

        // Assign values
        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MARCH, 4, 15, 20).getTime());

        // Define new day and month format
        try {
            dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            e.printStackTrace();
        }

        // Set listener
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                // Do something
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        // Show
        dateTimeDialogFragment.show(fragmentManager, "dialog_time");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView boxCode, cargo_date, box_info, truck, status, statusDetail;
        ImageView arrow;
        LinearLayout container;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            boxCode=itemView.findViewById(R.id.boxCode);
            cargo_date=itemView.findViewById(R.id.cargo_date);
            box_info=itemView.findViewById(R.id.box_info);
            truck=itemView.findViewById(R.id.truck);
            status=itemView.findViewById(R.id.status);
            statusDetail=itemView.findViewById(R.id.statusDetail);
            arrow=itemView.findViewById(R.id.arrow);
            container=itemView.findViewById(R.id.container);

        }
    }

    private void showDialog(@NotNull ViewHolder holder) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ofis_edit_address);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        Button save=dialog.findViewById(R.id.save);
        Button cancel=dialog.findViewById(R.id.cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Snackbar.make(holder.itemView, "Üstünlikli ýatda saklandy!", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
