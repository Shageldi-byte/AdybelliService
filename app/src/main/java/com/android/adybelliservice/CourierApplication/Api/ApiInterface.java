package com.android.adybelliservice.CourierApplication.Api;


import com.android.adybelliservice.CourierApplication.Model.Auth.Login;
import com.android.adybelliservice.CourierApplication.Model.Auth.LoginResponse;
import com.android.adybelliservice.CourierApplication.Model.Auth.Me;
import com.android.adybelliservice.CourierApplication.Model.Box.BoxResponse;
import com.android.adybelliservice.CourierApplication.Model.CourierResponse;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderDetailResponse;
import com.android.adybelliservice.CourierApplication.Model.OrderDetails.OrderProduct;
import com.android.adybelliservice.CourierApplication.Model.Orders.OneOrderResponse;
import com.android.adybelliservice.OfficeApplication.Model.MyBody;
import com.android.adybelliservice.OfficeApplication.Model.OrderDetails.EditOrder;

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
    Call<MyBody<com.android.adybelliservice.OfficeApplication.Model.Auth.LoginResponse>> login(@Body com.android.adybelliservice.OfficeApplication.Model.Auth.Login body);

    @Headers({
            "Accept: application/json",
            "u-sec: 1b755f986b72af7a6ea7eefaadc04b49d31c"
    })
    @GET("c4f6d27fb8d98a185f5a343ebdf2e52b34/get-order-details-for-courier?")
    Call<MyBody<ArrayList<OrderProduct>>> getOrderDetailsForCourier(@QueryMap Map<String, Object> queryMap,@Header("Authorization") String token);

    @Headers({
            "Accept: application/json",
            "u-sec: 1b755f986b72af7a6ea7eefaadc04b49d31c"
    })
    @POST("/c4f6d27fb8d98a185f5a343ebdf2e52b34/set-order-delivery")
    Call<ArrayList<Integer>> updateOrderDetail(@Body EditOrder body, @Header("Authorization") String token);

    @Headers({
            "Accept: application/json",
            "u-sec: 1b755f986b72af7a6ea7eefaadc04b49d31c"
    })
    @GET("/c4f6d27fb8d98a185f5a343ebdf2e52b34/get-order_by_id?")
    Call<MyBody<OneOrderResponse>> getOrderById(@Query("order_id") String order_id, @Header("Authorization") String token);


    //    OLD APIS
    @POST("/auth/login")
    Call<LoginResponse> login(@Body Login body);

    @GET("/auth/me")
    Call<Me> authMe(@Header("Authorization") String token);

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



//    @PATCH("/order-details/{id}")
//    Call<ArrayList<Integer>> updateOrderDetail(@Path("id") String id, @Body EditOrder body, @Header("Authorization") String token);

    @GET("/users/delivery")
    Call<CourierResponse> getCouriers(@Header("Authorization") String token);

    @GET("/orders/{id}")
    Call<OneOrderResponse> oneOrder(@Path("id") String id, @Header("Authorization") String token);



}
