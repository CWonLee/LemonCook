package com.makers.lemoncook.src.recipeList.interfaces;

import com.makers.lemoncook.src.recipeList.models.ResponseDeleteRecipe;
import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeListRetrofitInterface {
    @GET("/recipes")
    Call<ResponseGetRecipe> getRecipe(
            @Query("category") String category,
            @Query("filter") String filter,
            @Query("sort") String sort
    );

    @DELETE("/recipes/{recipeNo}")
    Call<ResponseDeleteRecipe> deleteRecipe(
            @Path("recipeNo") String recipeNo
    );
}
