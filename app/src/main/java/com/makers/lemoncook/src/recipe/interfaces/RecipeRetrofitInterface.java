package com.makers.lemoncook.src.recipe.interfaces;

import com.makers.lemoncook.src.recipe.models.RequestZZim;
import com.makers.lemoncook.src.recipe.models.ResponseDeleteRecipe;
import com.makers.lemoncook.src.recipe.models.ResponseDeleteShare;
import com.makers.lemoncook.src.recipe.models.ResponseDeleteZZim;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;
import com.makers.lemoncook.src.recipe.models.ResponseZZim;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeRetrofitInterface {
    @GET("/recipes/{recipeNo}")
    Call<ResponseRecipe> getRecipe(
            @Path("recipeNo") String recipeNo
    );

    @POST("/saveList")
    Call<ResponseZZim> postZZim(@Body RequestZZim params);

    @DELETE("/saveList")
    Call<ResponseDeleteZZim> deleteZZim(
            @Query("recipeNo") int recipeNo
    );

    @DELETE("/recipes/{recipeNo}")
    Call<ResponseDeleteRecipe> deleteRecipe(
            @Path("recipeNo") String recipeNo
    );

    @DELETE("/share")
    Call<ResponseDeleteShare> deleteShare(
            @Query("recipeNo") int recipeNo
    );
}
