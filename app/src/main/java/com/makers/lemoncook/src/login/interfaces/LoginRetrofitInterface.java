package com.makers.lemoncook.src.login.interfaces;

import com.makers.lemoncook.src.login.models.LoginRequest;
import com.makers.lemoncook.src.login.models.LoginResponse;
import com.makers.lemoncook.src.login.models.RequestShare;
import com.makers.lemoncook.src.login.models.ResponseShare;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRetrofitInterface {
    @POST("/signin/kakao")
    Call<LoginResponse> postLogin(@Body LoginRequest params);

    @POST("/share")
    Call<ResponseShare> postShare(@Body RequestShare params);
}
