package com.android.adybelliservice.StoreApplication.Adapter.Product;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.adybelliservice.StoreApplication.Api.APIClient;
import com.android.adybelliservice.StoreApplication.Api.ApiInterface;
import com.android.adybelliservice.StoreApplication.Common.ConfirmDialog;
import com.android.adybelliservice.StoreApplication.Common.LayoutType;
import com.android.adybelliservice.StoreApplication.Common.ProductStatus;
import com.android.adybelliservice.StoreApplication.Common.Utils;
import com.android.adybelliservice.StoreApplication.Model.Product.DeleteProduct;
import com.android.adybelliservice.StoreApplication.Model.Product.Product;
import com.android.adybelliservice.StoreApplication.Model.Product.ProductVisibilityBody;
import com.android.adybelliservice.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> products = new ArrayList<>();
    private Float passiveAlpha = 0.4f;
    private Float activeAlpha = 1f;
    private String status;
    public ProductAdapter(Context context, ArrayList<Product> products,String status) {
        this.context = context;
        this.products = products;
        this.status=status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_product_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(holder.getAdapterPosition());
        holder.name.setText(product.getName());

        holder.price.setText(product.getSale_price() + " TMT");
        if (product.getPrice() != null && product.getPrice() > 0) {
            holder.old_price.setText(product.getPrice() + " TMT");
            holder.old_price.setVisibility(View.VISIBLE);
        } else {
            holder.old_price.setVisibility(View.GONE);
        }

        checkCount(holder, product);


        if(status.equals(ProductStatus.ACCEPT)){
            holder.remove.setVisibility(View.GONE);
            holder.activeContainer.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.GONE);
        } else{
            holder.remove.setVisibility(View.VISIBLE);
            holder.activeContainer.setVisibility(View.GONE);
            holder.line.setVisibility(View.VISIBLE);
        }

        holder.mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mode.isChecked()) {
                    holder.mode.setChecked(!holder.mode.isChecked());
                    setProductVisibility(holder,product,holder.getAdapterPosition(),"1");
                } else {
                    holder.mode.setChecked(!holder.mode.isChecked());
                    setProductVisibility(holder,product,holder.getAdapterPosition(),"0");
                }

            }
        });



        String color = product.getColor_tm() + "";
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.store_placeholder))
                .load(product.getImage()).into(holder.image);
        if (Utils.getLanguage(context).equals("ru")) {
            holder.name.setText(product.getName_ru());
            color = product.getColor_ru() + "";
        }
        holder.remove.setTypeface(Utils.getRegularFont(context));
        holder.edit.setTypeface(Utils.getRegularFont(context));
        holder.old_price.setPaintFlags(holder.old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (product.getLayout_type() != null && product.getLayout_type().equals(LayoutType.PAGINATION)) {
            holder.defaultCon.setVisibility(View.GONE);
            holder.loading.setVisibility(View.VISIBLE);
        } else {
            holder.defaultCon.setVisibility(View.VISIBLE);
            holder.loading.setVisibility(View.GONE);
        }

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog confirmDialog = new ConfirmDialog(context);
                confirmDialog.setMessage(context.getResources().getString(R.string.removeMessage));
                confirmDialog.setTitle(context.getResources().getString(R.string.removeTitle));
                confirmDialog.setOkText(context.getResources().getString(R.string.ok));
                confirmDialog.setCancelText(context.getResources().getString(R.string.cancel));
                confirmDialog.getDialog().setCancelable(false);
                confirmDialog.setConfirmDialogListener(new ConfirmDialog.ConfirmDialogListener() {
                    @Override
                    public void onOkPressed() {
                        confirmDialog.getCancelButton().setEnabled(false);
                        confirmDialog.setLoading(true);
                        deleteProduct(product.getProd_id()+"",
                                holder.getAdapterPosition(),
                                holder,
                                confirmDialog);
                    }

                    @Override
                    public void onCancelPressed() {
                        confirmDialog.dismiss();
                        confirmDialog.setLoading(false);
                    }
                });
                confirmDialog.show();
            }
        });


    }

    private void deleteProduct(String id,int pos,ViewHolder holder,ConfirmDialog confirmDialog){
        ApiInterface apiInterface=APIClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call=apiInterface.deleteProduct("Bearer "+Utils.getSharedPreference(context,"token"),
                new DeleteProduct(id),
                "5c249d738fc4a4ae6f2844c0b744e06a");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    products.remove(pos);
                    notifyItemRemoved(pos);
                    Snackbar.make(holder.itemView,R.string.successfully,Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(holder.itemView,R.string.something_went_wrong,Snackbar.LENGTH_LONG).show();
                }
                confirmDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(holder.itemView,R.string.something_went_wrong,Snackbar.LENGTH_LONG).show();
                confirmDialog.dismiss();
            }
        });
    }

    private void setProductVisibility(ViewHolder holder, Product product, int pos, String visibility) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        ProductVisibilityBody body = new ProductVisibilityBody(product.getProd_id() + "", visibility);
        Call<ResponseBody> call = apiInterface.setProductVisibility("5c249d738fc4a4ae6f2844c0b744e06a", "Bearer " + Utils.getSharedPreference(context, "token"), body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
                if (response.isSuccessful()) {
                    alertDialogBuilder.setTitle(R.string.successfully);
                    alertDialogBuilder.setMessage(R.string.successfully);
                    if (visibility.equals("1")) {
                        try {
                            product.setIs_active(true);
                            products.set(holder.getAdapterPosition(), product);
                            holder.hasProduct.setText(context.getResources().getString(R.string.has_product));
                            checkCount(holder, product);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            product.setIs_active(false);
                            products.set(holder.getAdapterPosition(), product);
                            holder.hasProduct.setText(context.getResources().getString(R.string.no_product));
                            checkCount(holder, product);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    alertDialogBuilder.setTitle(R.string.something_went_wrong);
                    alertDialogBuilder.setMessage("Code: " + response.code());
                }
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
                alertDialogBuilder.setTitle(R.string.something_went_wrong);
                alertDialogBuilder.setMessage(t.getMessage());
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
                progressDialog.dismiss();
            }
        });
    }

    private void checkCount(ViewHolder holder, Product product) {
        if (product.getIs_active() == null || !product.getIs_active()) {
            if(status.equals(ProductStatus.ACCEPT))
                setAlpha(holder, passiveAlpha);
            holder.hasProduct.setText(context.getResources().getString(R.string.no_product));
            holder.mode.setChecked(false);
        } else {
            if(status.equals(ProductStatus.ACCEPT))
                setAlpha(holder, activeAlpha);
            holder.hasProduct.setText(context.getResources().getString(R.string.has_product));
            holder.mode.setChecked(true);
        }
    }

    private void setAlpha(ViewHolder holder, Float passiveAlpha) {
        holder.image.setAlpha(passiveAlpha);
        holder.name.setAlpha(passiveAlpha);
        holder.size.setAlpha(passiveAlpha);
        holder.size_color.setAlpha(passiveAlpha);
        holder.old_price.setAlpha(passiveAlpha);
        holder.price.setAlpha(passiveAlpha);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView size;
        private TextView size_color;
        private TextView old_price;
        private Button edit, remove;
        private ImageView image;
        private RelativeLayout defaultCon, top;
        private LottieAnimationView loading;
        private ImageButton minus, plus;
        private TextView count, hasProduct;
        private SwitchCompat mode;
        private LinearLayout activeContainer;
        private View line;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            size_color = itemView.findViewById(R.id.size_color);
            price = itemView.findViewById(R.id.price);
            old_price = itemView.findViewById(R.id.old_price);
            edit = itemView.findViewById(R.id.edit);
            remove = itemView.findViewById(R.id.remove);
            image = itemView.findViewById(R.id.image);
            size = itemView.findViewById(R.id.size);
            defaultCon = itemView.findViewById(R.id.defaultCon);
            loading = itemView.findViewById(R.id.loading);
            top = itemView.findViewById(R.id.top);
            count = itemView.findViewById(R.id.count);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            mode = itemView.findViewById(R.id.mode);
            hasProduct = itemView.findViewById(R.id.hasProduct);
            activeContainer = itemView.findViewById(R.id.activeContainer);
            line = itemView.findViewById(R.id.line);
        }
    }
}
