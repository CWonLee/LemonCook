package com.makers.lemoncook.src.recipe.interfaces;

import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecipeRetrofitInterface {
    @GET("/recipes/{recipeNo}")
    Call<ResponseRecipe> getRecipe(
            @Path("recipeNo") String recipeNo
    );
}
