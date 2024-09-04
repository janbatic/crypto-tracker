package com.example.mobile_app;
import com.example.mobile_app.responses.CryptoInfoResponse;
import com.example.mobile_app.responses.LoginResponse;
import com.example.mobile_app.responses.PortfolioResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("crypto-info/")
    Call<CryptoInfoResponse> getCryptocurrencies(@Query("page") int page);

    @FormUrlEncoded
    @POST("user/login/")
    Call<LoginResponse> login(@Field("username")String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register/")
    Call<LoginResponse> register(@Field("username")String username,@Field("email")String email, @Field("password") String password, @Field("password_again") String password_again);

    @FormUrlEncoded
    @POST("portfolio/transaction/")
    Call<Void> transaction(
            @Header("Authorization") String authHeader,
            @Field("cryptocurrency")String cryptocurrency,
            @Field("amount")String amount,
            @Field("transaction_type") String transaction_type);


    @GET("portfolio/possession/")
    Call<PortfolioResponse> getPortfolio(@Header("Authorization") String authHeader);

}
