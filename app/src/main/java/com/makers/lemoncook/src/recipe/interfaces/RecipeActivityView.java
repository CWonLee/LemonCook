package com.makers.lemoncook.src.recipe.interfaces;

import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

public interface RecipeActivityView {
    void getRecipeSuccess(boolean isSuccess, int code, String message, ResponseRecipe.Result result);

    void getRecipeFailure();

    void getInterface(RecipeRecyclerViewInterface recipeRecyclerViewInterface);

    void change(int idx);

    void postZZimSuccess(boolean isSuccess, int code, String message);

    void postZZimFailure();

    void deleteZZimSuccess(boolean isSuccess, int code, String message);

    void deleteZZimFailure();
}
