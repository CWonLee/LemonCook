package com.makers.lemoncook.src.recipeList.interfaces;

import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;

import java.util.ArrayList;

public interface RecipeListActivityView {
    void getRecipeSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseGetRecipe.Result> result, boolean clearData);

    void getRecipeFailure();

    void deleteRecipeSuccess(boolean isSuccess, int code, String message, int idx);

    void deleteRecipeFailure();

    void deleteRecipe(int recipeNo, int idx);
}
