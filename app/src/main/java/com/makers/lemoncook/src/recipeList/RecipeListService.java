package com.makers.lemoncook.src.recipeList;

import com.makers.lemoncook.src.recipeList.interfaces.RecipeListActivityView;
import com.makers.lemoncook.src.recipeList.interfaces.RecipeListRetrofitInterface;
import com.makers.lemoncook.src.recipeList.models.ResponseDeleteRecipe;
import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class RecipeListService {
    private final RecipeListActivityView mRecipeListActivityView;

    RecipeListService(final RecipeListActivityView recipeListActivityView) {
        this.mRecipeListActivityView = recipeListActivityView;
    }

    void getRecipe(String category, String filter, String sort) {
        final RecipeListRetrofitInterface recipeListRetrofitInterface = getRetrofit().create(RecipeListRetrofitInterface.class);
        recipeListRetrofitInterface.getRecipe(category, filter, sort).enqueue(new Callback<ResponseGetRecipe>() {
            @Override
            public void onResponse(Call<ResponseGetRecipe> call, Response<ResponseGetRecipe> response) {
                final ResponseGetRecipe responseGetRecipe = response.body();
                mRecipeListActivityView.getRecipeSuccess(responseGetRecipe.isSuccess(), responseGetRecipe.getCode(), responseGetRecipe.getMessage(), responseGetRecipe.getResult());
            }

            @Override
            public void onFailure(Call<ResponseGetRecipe> call, Throwable t) {
                t.printStackTrace();
                mRecipeListActivityView.getRecipeFailure();
            }
        });
    }

    void deleteRecipe(String recipeNo, final int idx) {
        final RecipeListRetrofitInterface recipeListRetrofitInterface = getRetrofit().create(RecipeListRetrofitInterface.class);
        recipeListRetrofitInterface.deleteRecipe(recipeNo).enqueue(new Callback<ResponseDeleteRecipe>() {
            @Override
            public void onResponse(Call<ResponseDeleteRecipe> call, Response<ResponseDeleteRecipe> response) {
                final ResponseDeleteRecipe responseDeleteRecipe = response.body();
                mRecipeListActivityView.deleteRecipeSuccess(responseDeleteRecipe.isSuccess(), responseDeleteRecipe.getCode(), responseDeleteRecipe.getMessage(), idx);
            }

            @Override
            public void onFailure(Call<ResponseDeleteRecipe> call, Throwable t) {
                t.printStackTrace();
                mRecipeListActivityView.deleteRecipeFailure();
            }
        });
    }
}
