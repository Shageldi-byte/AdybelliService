package com.android.adybelliservice.StoreApplication.Api;

import com.android.adybelliservice.StoreApplication.Model.AddProduct.AddProductBody;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.AddResponse;
import com.android.adybelliservice.StoreApplication.Model.AddProduct.ImageResponse;
import com.android.adybelliservice.StoreApplication.Model.Filter.GetAddInfo;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOption;
import com.android.adybelliservice.StoreApplication.Model.Filter.ProductOptionPost;
import com.android.adybelliservice.StoreApplication.Model.GBody;
import com.android.adybelliservice.StoreApplication.Model.Login.LoginBody;
import com.android.adybelliservice.StoreApplication.Model.Login.LoginRequest;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserOrdersResponse;
import com.android.adybelliservice.StoreApplication.Model.Orders.GetUserSingleOrderResponse;
import com.android.adybelliservice.StoreApplication.Model.Orders.OrderStatusBody;
import com.android.adybelliservice.StoreApplication.Model.Product.DeleteProduct;
import com.android.adybelliservice.StoreApplication.Model.Product.GetAcceptedProductBody;
import com.android.adybelliservice.StoreApplication.Model.Product.GetProductBody;
import com.android.adybelliservice.StoreApplication.Model.Product.Product;
import com.android.adybelliservice.StoreApplication.Model.Product.ProductVisibilityBody;
import com.android.adybelliservice.StoreApplication.Model.Profile.CountResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("api/public/signin")
    Call<GBody<LoginBody>> signIn(@Body LoginRequest body,@Header("Rank") String rank);

    @GET("api/public/get-add-product-info")
    Call<GBody<GetAddInfo>> getAddInfo(@Header("Rank") String rank,@Header("Authorization") String token);

    @POST("api/public/get-products")
    Call<GBody<ArrayList<Product>>> getProducts(@Header("Rank") String rank, @Header("Authorization") String token, @Body GetProductBody body);

    @Multipart
    @POST("api/public/f406a782196ee998c4decb47a14736a9-sr")
    Call<GBody<ImageResponse>> addProductImage(@Part MultipartBody.Part image, @Header("Authorization") String token);

    @POST("api/public/f406a782196ee998c4decb47a14736a9")
    Call<GBody<AddResponse>> addProduct(@Header("Rank") String rank, @Header("Authorization") String token, @Body AddProductBody body);

    @GET("api/public/get-orders?")
    Call<GetUserOrdersResponse> getUserOrders(@Query("page") int page,@Query("per_page") int per_page,@Header("Rank") String rank,@Header("Authorization") String token);

    @GET("api/public/get-order?")
    Call<GetUserSingleOrderResponse> getUserSingleOrder(@Query("order_id") String order_id,@Header("Rank") String rank,@Header("Authorization") String token);

    @POST("api/public/set-order-product-status")
    Call<GBody<String>> setOrderProductStatus(@Header("Authorization") String token, @Header("Rank") String rank, @Body OrderStatusBody body);

    @POST("api/public/set-product-visibility")
    Call<ResponseBody> setProductVisibility(@Header("Rank") String rank, @Header("Authorization") String token,@Body ProductVisibilityBody body);

    @POST("api/public/get-accepted-products-options")
    Call<ProductOption> getProductsOptions(@Body ProductOptionPost body, @Header("Rank") String rank,@Header("Authorization") String token);

    @HTTP(method = "DELETE", path = "api/public/delete-seller-product", hasBody = true)
    Call<ResponseBody> deleteProduct(@Header("Authorization") String token, @Body DeleteProduct body, @Header("Rank") String rank);

    @POST("api/public/get-accepted-products")
    Call<GBody<ArrayList<Product>>> getAcceptedProducts(@Header("Rank") String rank, @Header("Authorization") String token, @Body GetAcceptedProductBody body);

    @GET("api/public/get-seller-product-count")
    Call<GBody<CountResponse>> getSellerProductCount(@Header("Authorization") String token,@Header("Rank") String rank);
}
