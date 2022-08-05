package com.android.adybelliservice.CourierApplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.CourierApplication.Adapter.InTruckAdapter;
import com.android.adybelliservice.CourierApplication.Api.APIClient;
import com.android.adybelliservice.CourierApplication.Api.ApiInterface;
import com.android.adybelliservice.CourierApplication.Common.BottomSheetDialog;
import com.android.adybelliservice.CourierApplication.Common.Constant;
import com.android.adybelliservice.CourierApplication.Common.Utils;
import com.android.adybelliservice.CourierApplication.Database.OneOrderDB;
import com.android.adybelliservice.CourierApplication.Model.Box.StatusDetail;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.CourierApplication.Model.Orders.OneOrderResponse;
import com.android.adybelliservice.CourierApplication.Model.Orders.OrderUser;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.CourierActivityMainBinding;
import com.android.adybelliservice.databinding.CourierActivityMoreInfoBinding;
//import com.android.adybelliservice.CourierApplication.databinding.ActivityMoreInfoBinding;

import java.text.ParseException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreInfo extends AppCompatActivity {
    private RecyclerView productRec;
    private Context context = this;
    private ArrayList<OrderProduct> products = new ArrayList<>();
    private CourierActivityMoreInfoBinding binding;
    private String orderId;
    private String phone = "";
    private OneOrderDB oneOrderDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CourierActivityMoreInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        oneOrderDB = new OneOrderDB(context);
        orderId = getIntent().getStringExtra("id");
        initComponents();

        if (Utils.getSharedPreference(context,"isInternet").equals("yes")) {
            request();
        } else {
            offlineMode();
        }
    }

    private void offlineMode() {
        Cursor cursor = oneOrderDB.getById(orderId);
        OneOrderResponse oneOrderResponse=null;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                phone=cursor.getString(13);
                binding.sany.setText("0");
                oneOrderResponse=new OneOrderResponse(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        Double.parseDouble(cursor.getString(4)),
                        Double.parseDouble(cursor.getString(5)),
                        cursor.getString(6),
                        Integer.parseInt(cursor.getString(7)),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        products
                );
//                oneOrderResponse = new OneOrderResponse(
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getString(3),
//                        Double.parseDouble(cursor.getString(4)),
//                        Double.parseDouble(cursor.getString(5)),
//                        cursor.getString(6),
//                        Integer.parseInt(cursor.getString(7)),
//                        cursor.getString(8),
//                        cursor.getString(9),
//                        cursor.getString(10),
//                        new OrderUser(cursor.getString(3), cursor.getString(11), cursor.getString(12), cursor.getString(13)),
//                        products
//                );
            }
            setInfo(oneOrderResponse);
        }
    }

    private void request() {
        products.clear();
        ProgressDialog progressDialog = Utils.progressDialog(context, "Biraz garasyn...", "Maglumatlar yuklenyar!", true);
        progressDialog.show();
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<MyBody<OneOrderResponse>> call = apiInterface.getOrderById(orderId, "Bearer " + Utils.getSharedPreference(context, "tkn"));
        call.enqueue(new Callback<MyBody<OneOrderResponse>>() {
            @Override
            public void onResponse(Call<MyBody<OneOrderResponse>> call, Response<MyBody<OneOrderResponse>> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    OneOrderResponse order = response.body().getBody();
                    if (order.getProducts() != null) {
                        products.addAll(order.getProducts());
//                        ArrayList<OrderProduct> temp = new ArrayList<>();
//                        for (OrderProduct product : products) {
//                            if (product.getDelivery_id() != null) {
//                                String kl = product.getDelivery_id() + "";
//                                if (kl.equals(Utils.getSharedPreference(context, "id"))) {
//                                    temp.add(product);
//                                }
//                            }
//                        }
                        setProducts(products);
                    } else {
                        binding.sany.setText("0");
                    }
                    insertDB(order);
                    setInfo(order);
                } else {
                    Utils.refreshToken(context);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyBody<OneOrderResponse>> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void insertDB(OneOrderResponse order) {
        oneOrderDB.deleteData(orderId);
        oneOrderDB.insert(order.getOrder_id(),
                order.getStatus(),
                order.getUser_id(),
                order.getTotal() + "",
                order.getShipping_price() + "",
                order.getDelivery_day(),
                order.getPayment_method() + "",
                order.getAddress(),
                order.getCreated_at(),
                order.getUpdated_at(),
                order.getFullname(),
                order.getEmail(),
                order.getPhone());
    }

    private void setInfo(OneOrderResponse order) {
        if (order != null) {
            if (order.getPhone() != null) {
                phone = order.getPhone();
                binding.userPhone.setText(order.getPhone());
            }
            if (order.getFullname() != null)
                binding.userFullName.setText(order.getFullname());

        }
        if (order.getAddress() != null)
            binding.address.setText(order.getAddress());
        if (order.getOrder_id() != null)
            binding.orderId.setText(order.getOrder_id());
        try {
            Log.e("Date",Utils.covertStringToDate(order.getCreated_at()));
            binding.dateOfOrder.setText(Utils.covertStringToDate(order.getCreated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
            binding.dateOfOrder.setText("");
        }
        if (order.getPayment_method() == 1)
            binding.paymentMethod.setText("Gapyda nagt toleg");
        else
            binding.paymentMethod.setText("Gapyda terminal");
        binding.shippingPrice.setText(order.getShipping_price() + " TMT");
        switch (order.getStatus()){
            case Constant.ONTHEWAY:
                binding.status.setText("Ýolda");
                break;
            case Constant.CANCELED:
            case Constant.REJECTED:
                binding.status.setText("Gaýtaryldy");
                break;
            case Constant.ACCEPTED:
                binding.status.setText("Kabul edildi");
                break;
            case Constant.DELIVERED:
                binding.status.setText("Eltip berildi");
                break;
        }
        binding.totalPrice.setText(order.getTotal() + " TMT");
        binding.userAddressPhone.setText("");
        binding.addressName.setText("");

    }

    private void initComponents() {
        productRec = findViewById(R.id.productRec);
    }

    private void setProducts(ArrayList<OrderProduct> arrayList) {
        InTruckAdapter adapter = new InTruckAdapter(context, arrayList, "");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        productRec.setNestedScrollingEnabled(false);
        productRec.setAdapter(adapter);
        productRec.setLayoutManager(manager);
        binding.sany.setText(arrayList.size() + "");
    }

    public void call(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void showCancelDialog(View view) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }

    public void back(View view) {
        finish();
    }
}