package com.makers.lemoncook.src.myPage.interfaces;

import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyPageRetrofitInterface {
    @GET("/mypage")
    Call<ResponseGetMyPage> getMyPage(
            @Query("page") int page,
            @Query("tab") String tab
    );
}
