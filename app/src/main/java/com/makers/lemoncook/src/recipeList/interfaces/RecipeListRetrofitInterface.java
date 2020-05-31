package com.makers.lemoncook.src.recipeList.interfaces;

import com.makers.lemoncook.src.recipeList.models.RequestPostZZim;
import com.makers.lemoncook.src.recipeList.models.ResponseDeleteRecipe;
import com.makers.lemoncook.src.recipeList.models.ResponseDeleteZZim;
import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;
import com.makers.lemoncook.src.recipeList.models.ResponsePostZZim;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeListRetrofitInterface {
    @GET("/recipes")
    Call<ResponseGetRecipe> getRecipe(
            @Query("category") String category,
            @Query("filter") String filter,
            @Query("sort") String sort,
            @Query("page") int page
    );

    @DELETE("/recipes/{recipeNo}")
    Call<ResponseDeleteRecipe> deleteRecipe(
            @Path("recipeNo") String recipeNo
    );

    @POST("/saveList")
    Call<ResponsePostZZim> postZZim(@Body RequestPostZZim params);

    @DELETE("/saveList")
    Call<ResponseDeleteZZim> deleteZZim(
            @Query("recipeNo") int recipeNo
    );
}
