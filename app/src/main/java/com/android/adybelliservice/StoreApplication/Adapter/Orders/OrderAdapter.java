package com.android.adybelliservice.StoreApplication.Adapter.Orders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.StoreApplication.Activity.MainActivity;
import com.android.adybelliservice.StoreApplication.Common.Constant;
import com.android.adybelliservice.StoreApplication.Common.OrderStatus;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Fragment.Orders.MoreInfo;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserOrdersResponseBody;
import com.android.adybelliservice.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<GetUserOrdersResponseBody> orders = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;

    public OrderAdapter(ArrayList<GetUserOrdersResponseBody> orders, Context context, FragmentManager fragmentManager) {
        this.orders = orders;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_order_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderAdapter.ViewHolder holder, int position) {
        try {
            GetUserOrdersResponseBody order = orders.get(position);
            if (order.getTotal() != null) {
                holder.cost.setText(order.getTotal() + " TMT");
            } else {
                holder.cost.setText("0 TMT");
            }

            if (order.getProducts() != null) {

                holder.colorVariantsContainer.removeAllViews();
                ArrayList<String> colorVariants = new ArrayList<>();
                Gson gson = new Gson();
                JSONArray array=new JSONArray(order.getProducts());
                for(int i=0;i<array.length();i++){
                    JSONObject jb = (JSONObject)array.getJSONObject(i);
                    colorVariants.add(jb.getString("image"));
                    Log.e("Image",jb.getString("image"));
                }
                for (String image : colorVariants) {
                    RoundedImageView imageView = new RoundedImageView(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)context.getResources().getDimension(R.dimen.order_product_width), (int)context.getResources().getDimension(R.dimen.order_product_height));
                    layoutParams.setMargins(30, 0, 0, 0);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setPadding(2, 2, 2, 2);
                    imageView.setBackgroundResource(R.drawable.store_order_stroke);
                    imageView.setCornerRadius(8f);
                    Glide.with(context)
                            .load(Constant.SERVER_URL + image)
                            .placeholder(R.drawable.store_placeholder)
                            .into(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ArrayList<String> imgs = new ArrayList<>();
                            imgs.add(image);
//                            Util.showImageViewer(context, imgs, imgs);
                        }
                    });

                    holder.colorVariantsContainer.addView(imageView);

                }
            }

            holder.id.setText(order.getOrder_id()+"");

            switch (order.getStatus()){
                case OrderStatus.PENDING:
                    changeStatus(holder,R.drawable.store_ic_baseline_access_time_filled_24,R.string.pending_status,R.color.passive_color2);
                    break;
                case OrderStatus.ACCEPTED:
                    changeStatus(holder,R.drawable.store_ic_baseline_check_circle_24,R.string.accepted_status,R.color.success);

                    break;
                case OrderStatus.REJECTED:
                    changeStatus(holder,R.drawable.store_ic_cancel,R.string.rejeted_status,R.color.red);
                    break;
                case OrderStatus.CANCELED:
                    changeStatus(holder,R.drawable.store_ic_warning,R.string.canceled_status,R.color.red);
                    break;

            }

            if(order.getCreated_at()!=null){
                holder.date.setText(order.getCreated_at());
                String date=order.getCreated_at();
                SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss");
                Date newDate= null;
                try {
                    newDate = spf.parse(date);
                    spf= new SimpleDateFormat("dd.MM.yyyy, HH:mm");
                    date = spf.format(newDate);
                    holder.date.setText(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else{
                holder.date.setText("");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MoreInfo moreInfo=MoreInfo.newInstance(order.getOrder_id());
                    Utils.hideAdd(moreInfo, MoreInfo.class.getSimpleName(), fragmentManager, R.id.content);
                    MainActivity.thirdFragment = moreInfo;
                }
            });


            holder.moreInfo.setTypeface(Utils.getBoldFont(context));
            holder.cost.setTypeface(Utils.getMediumFont(context));
            holder.date.setTypeface(Utils.getMediumFont(context));
            holder.id.setTypeface(Utils.getMediumFont(context));
            holder.costTitle.setTypeface(Utils.getRegularFont(context));
            holder.status.setTypeface(Utils.getMediumFont(context));




        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void changeStatus(ViewHolder holder, int icon, int status, int color) {
        holder.status.setText(Utils.getStringResource(status,context));
        holder.status.setTextColor(context.getResources().getColor(color));
        holder.status_icon.setImageResource(icon);
        holder.status_icon.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, cost, status, moreInfo, costTitle,date;
        LinearLayout colorVariantsContainer;
        ImageView status_icon;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            colorVariantsContainer = itemView.findViewById(R.id.colorVariantsContainer);
            id = itemView.findViewById(R.id.id);
            cost = itemView.findViewById(R.id.cost);
            status = itemView.findViewById(R.id.status);
            moreInfo = itemView.findViewById(R.id.moreInfo);
            costTitle = itemView.findViewById(R.id.costTitle);
            date = itemView.findViewById(R.id.date);
            status_icon = itemView.findViewById(R.id.status_icon);
        }
    }


}
