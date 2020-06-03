package com.makers.lemoncook.src.recipe;

import com.makers.lemoncook.src.recipe.interfaces.RecipeActivityView;
import com.makers.lemoncook.src.recipe.interfaces.RecipeRetrofitInterface;
import com.makers.lemoncook.src.recipe.models.RequestZZim;
import com.makers.lemoncook.src.recipe.models.ResponseDeleteRecipe;
import com.makers.lemoncook.src.recipe.models.ResponseDeleteZZim;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;
import com.makers.lemoncook.src.recipe.models.ResponseZZim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class RecipeService {
    private final RecipeActivityView mRecipeActivityView;

    RecipeService(final RecipeActivityView recipeActivityView) {
        this.mRecipeActivityView = recipeActivityView;
    }

    void getRecipe(int recipeNo) {
        final RecipeRetrofitInterface recipeRetrofitInterface = getRetrofit().create(RecipeRetrofitInterface.class);
        recipeRetrofitInterface.getRecipe(Integer.toString(recipeNo)).enqueue(new Callback<ResponseRecipe>() {
            @Override
            public void onResponse(Call<ResponseRecipe> call, Response<ResponseRecipe> response) {
                final ResponseRecipe responseRecipe = response.body();
                mRecipeActivityView.getRecipeSuccess(responseRecipe.isSuccess(), responseRecipe.getCode(), responseRecipe.getMessage(), responseRecipe.getResult());
            }

            @Override
            public void onFailure(Call<ResponseRecipe> call, Throwable t) {
                t.printStackTrace();
                mRecipeActivityView.getRecipeFailure();
            }
        });
    }

    void postZZim(RequestZZim requestZZim) {
        final RecipeRetrofitInterface recipeRetrofitInterface = getRetrofit().create(RecipeRetrofitInterface.class);
        recipeRetrofitInterface.postZZim(requestZZim).enqueue(new Callback<ResponseZZim>() {
            @Override
            public void onResponse(Call<ResponseZZim> call, Response<ResponseZZim> response) {
                final ResponseZZim responseZZim = response.body();
                mRecipeActivityView.postZZimSuccess(responseZZim.isSuccess(), responseZZim.getCode(), responseZZim.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseZZim> call, Throwable t) {
                t.printStackTrace();
                mRecipeActivityView.postZZimFailure();
            }
        });
    }

    void deleteZZim(int recipeNo) {
        final RecipeRetrofitInterface recipeRetrofitInterface = getRetrofit().create(RecipeRetrofitInterface.class);
        recipeRetrofitInterface.deleteZZim(recipeNo).enqueue(new Callback<ResponseDeleteZZim>() {
            @Override
            public void onResponse(Call<ResponseDeleteZZim> call, Response<ResponseDeleteZZim> response) {
                final ResponseDeleteZZim responseDeleteZZim = response.body();
                mRecipeActivityView.deleteZZimSuccess(responseDeleteZZim.isSuccess(), responseDeleteZZim.getCode(), responseDeleteZZim.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseDeleteZZim> call, Throwable t) {
                t.printStackTrace();
                mRecipeActivityView.deleteZZimFailure();
            }
        });
    }

    void deleteRecipe(String recipeNo) {
        final RecipeRetrofitInterface recipeRetrofitInterface = getRetrofit().create(RecipeRetrofitInterface.class);
        recipeRetrofitInterface.deleteRecipe(recipeNo).enqueue(new Callback<ResponseDeleteRecipe>() {
            @Override
            public void onResponse(Call<ResponseDeleteRecipe> call, Response<ResponseDeleteRecipe> response) {
                final ResponseDeleteRecipe responseDeleteRecipe = response.body();
                mRecipeActivityView.deleteRecipeSuccess(responseDeleteRecipe.isSuccess(), responseDeleteRecipe.getCode(), responseDeleteRecipe.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseDeleteRecipe> call, Throwable t) {
                t.printStackTrace();
                mRecipeActivityView.deleteRecipeFailure();
            }
        });
    }
}
