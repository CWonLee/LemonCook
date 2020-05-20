package com.makers.lemoncook.src.recipeList.interfaces;

import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;

import java.util.ArrayList;

public interface RecipeListActivityView {
    void getRecipeSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseGetRecipe.Result> result);

    void getRecipeFailure();
}
