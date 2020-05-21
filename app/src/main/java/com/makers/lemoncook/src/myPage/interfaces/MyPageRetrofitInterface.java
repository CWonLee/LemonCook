package com.makers.lemoncook.src.myPage.interfaces;

import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyPageRetrofitInterface {
    @GET("/mypage")
    Call<ResponseGetMyPage> getMyPage();
}
