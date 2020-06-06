package com.makers.lemoncook.src.loadRecipe.interfaces;

import com.makers.lemoncook.src.recipe.models.ResponseRecipe;
import com.makers.lemoncook.src.search.models.ResponseSearch;

import java.util.ArrayList;

public interface LoadActivityView {
    void searchSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseSearch.Result> result, boolean clearData);

    void searchFailure();

    void loadRecipeNo(int recipeNo);
}
