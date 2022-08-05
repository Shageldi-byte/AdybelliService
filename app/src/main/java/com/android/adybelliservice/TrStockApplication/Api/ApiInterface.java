package com.android.adybelliservice.TrStockApplication.Api;

import com.android.adybelliservice.OfficeApplication.Model.Auth.Login;
import com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse;
import com.android.adybelliservice.OfficeApplication.Model.Box.BoxResponse;
import com.android.adybelliservice.OfficeApplication.Model.CourierResponse;
import com.android.adybelliservice.OfficeApplication.Model.EditAddress;
import com.android.adybelliservice.OfficeApplication.Model.InStockModel;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderDetailResponse;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.TrStockApplication.Model.AddBox;
import com.android.adybelliservice.TrStockApplication.Model.Box;
import com.android.adybelliservice.TrStockApplication.Model.ChangeOrderStatusOrdered;
import com.android.adybelliservice.TrStockApplication.Model.ChangeProductBox;
import com.android.adybelliservice.TrStockApplication.Model.ModifyBoxStatus;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;
import com.android.adybelliservice.TrStockApplication.Model.OrderToBox;
import com.android.adybelliservice.TrStockApplication.Model.SetTruckName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    String u_sec="1b755f986b72af7a6ea7eefaadc04b49d31c";
    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @GET("/c4f6d27fb8d98a185f5a343ebdf2e52b34/get-order-details?")
    Call<MyBody<ArrayList<OrderDetail>>> getOrderDetails(@Header("Authorization") String token, @QueryMap Map<String,Object> params);

    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/change-order-status-ordered")
    Call<MyBody<String>> changeOrderStatusOrdered(@Header("Authorization") String token, @Body ChangeOrderStatusOrdered body);


    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/change-order-status-canceled")
    Call<MyBody<String>> changeOrderStatusCanceled(@Header("Authorization") String token, @Body ChangeOrderStatusOrdered body);

    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @GET("/c4f6d27fb8d98a185f5a343ebdf2e52b34/get-boxes?")
    Call<MyBody<ArrayList<Box>>> getBoxes(@Header("Authorization") String token, @QueryMap Map<String,Object> params);


    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/create-box")
    Call<MyBody<String>> createBox(@Header("Authorization") String token, @Body AddBox body);



    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/set-truck-name")
    Call<MyBody<String>> setTruckName(@Header("Authorization") String token, @Body SetTruckName body);


    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/modify-box-status")
    Call<MyBody<String>> modifyBoxStatus(@Header("Authorization") String token, @Body ModifyBoxStatus body);


    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/order-to-box")
    Call<MyBody<String>> orderToBox(@Header("Authorization") String token, @Body OrderToBox body);

    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/change-product-box")
    Call<MyBody<String>> changeProductBox(@Header("Authorization") String token, @Body ChangeProductBox body);

    @Headers({
            "Accept: application/json",
            "u-sec: "+u_sec
    })
    @GET("/c4f6d27fb8d98a185f5a343ebdf2e52b34/get-boxed-products?")
    Call<MyBody<ArrayList<OrderDetail>>> getBoxedProducts(@Header("Authorization") String token, @QueryMap Map<String,Object> params);



}
