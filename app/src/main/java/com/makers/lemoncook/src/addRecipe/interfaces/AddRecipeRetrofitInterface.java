package com.makers.lemoncook.src.addRecipe.interfaces;

import com.makers.lemoncook.src.addRecipe.models.ResponseGetMaterial;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddRecipeRetrofitInterface {
    @GET("/recipeinfo/ingredient")
    Call<ResponseGetMaterial> getMaterial(
            @Query("search") String search
    );
}
