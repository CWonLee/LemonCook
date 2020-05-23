package com.makers.lemoncook.src.search.interfaces;

import com.makers.lemoncook.src.search.models.ResponseSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchRetrofitInterface {
    @GET("/recipes")
    Call<ResponseSearch> getSearch(
            @Query("search") String search,
            @Query("filter") String filter,
            @Query("sort") String sort
    );
}
