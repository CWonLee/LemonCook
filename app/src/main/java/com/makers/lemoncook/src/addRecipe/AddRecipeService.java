package com.makers.lemoncook.src.addRecipe;

import com.makers.lemoncook.src.addRecipe.interfaces.AddRecipeActivityView;
import com.makers.lemoncook.src.recipe.interfaces.RecipeRetrofitInterface;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class AddRecipeService {
    private final AddRecipeActivityView mAddRecipeActivityView;

    AddRecipeService(final AddRecipeActivityView addRecipeActivityView) {
        this.mAddRecipeActivityView = addRecipeActivityView;
    }

    void getRecipe(int recipeNo) {
        final RecipeRetrofitInterface recipeRetrofitInterface = getRetrofit().create(RecipeRetrofitInterface.class);
        recipeRetrofitInterface.getRecipe(Integer.toString(recipeNo)).enqueue(new Callback<ResponseRecipe>() {
            @Override
            public void onResponse(Call<ResponseRecipe> call, Response<ResponseRecipe> response) {
                final ResponseRecipe responseRecipe = response.body();
                mAddRecipeActivityView.getRecipeSuccess(responseRecipe.isSuccess(), responseRecipe.getCode(), responseRecipe.getMessage(), responseRecipe.getResult());
            }

            @Override
            public void onFailure(Call<ResponseRecipe> call, Throwable t) {
                t.printStackTrace();
                mAddRecipeActivityView.getRecipeFailure();
            }
        });
    }
}
