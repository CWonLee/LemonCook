package com.makers.lemoncook.src.join.interfaces;

import com.makers.lemoncook.src.join.models.JoinRequest;
import com.makers.lemoncook.src.join.models.JoinResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JoinRetrofitInterface  {
    @POST("/users")
    Call<JoinResponse> postJoin(@Body JoinRequest params);
}
