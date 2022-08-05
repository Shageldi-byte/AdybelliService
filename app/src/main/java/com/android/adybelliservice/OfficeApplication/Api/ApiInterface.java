package com.android.adybelliservice.OfficeApplication.Api;

import com.android.adybelliservice.OfficeApplication.Model.Auth.Login;
import com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse;
import com.android.adybelliservice.OfficeApplication.Model.Box.BoxResponse;
import com.android.adybelliservice.OfficeApplication.Model.Courier;
import com.android.adybelliservice.OfficeApplication.Model.CourierResponse;
import com.android.adybelliservice.OfficeApplication.Model.EditAddress;
import com.android.adybelliservice.OfficeApplication.Model.InStockModel;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderDetailResponse;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.TrStockApplication.Model.OrderDetail;

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

    @Headers({
            "Accept: application/json",
            "u-sec: 1b755f986b72af7a6ea7eefaad0588c04b49d31c"
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/signin")
    Call<MyBody<LoginResponse>> login(@Body Login body);

    @Headers({
            "Accept: application/json",
            "u-sec: 1b755f986b72af7a6ea7eefaadc04b49d31c"
    })
    @GET("/c4f6d27fb8d98a185f5a343ebdf2e52b34/get-boxes?")
    Call<MyBody<ArrayList<InStockModel>>> getBoxes(@Header("Authorization") String token,@QueryMap HashMap<String,Object> params);

    @Headers({
            "Accept: application/json",
            "u-sec: 1b755f986b72af7a6ea7eefaadc04b49d31c"
    })
    @GET("/c4f6d27fb8d98a185f5a343ebdf2e52b34/get-boxed-products?")
    Call<MyBody<ArrayList<OrderProduct>>> getBoxedProducts(@Header("Authorization") String token,@QueryMap HashMap<String,Object> params);

    @Headers({
            "Accept: application/json",
            "u-sec: 1b755f986b72af7a6ea7eefaadc04b49d31c"
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/set-order-delivery")
    Call<MyBody<String>> updateOrderDetail(@Body EditOrder body, @Header("Authorization") String token);


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
    @GET("/c4f6d27fb8d98a185f5a343ebdf2e52b34/get-couriers")
    Call<MyBody<ArrayList<Courier>>> getCouriers(@Header("Authorization") String token);

//    Old requests

    @GET("/boxes/trucks")
    Call<ArrayList<String>> getTrucks(@Header("Authorization") String token);

    @GET("/auth/refresh")
    Call<LoginResponse> refreshToken(@Header("Authorization") String token);

    @GET("/boxes?")
    Call<BoxResponse> getBoxesByStatus(@Header("Authorization") String token, @Query("status") String status);

    @GET("/boxes?")
    Call<BoxResponse> getBoxesByStatusAndNameTruck(@Header("Authorization") String token, @Query("status") String status, @Query("name_truck") String truck);

    @GET("/order-details?")
    Call<OrderDetailResponse> orderDetails(@QueryMap Map<String, Object> queryParams, @Header("Authorization") String token);




    @PATCH("/orders/{id}")
    Call<ArrayList<Integer>> editAddress(@Path("id") String id, @Header("Authorization") String token, @Body EditAddress editAddress);


}
