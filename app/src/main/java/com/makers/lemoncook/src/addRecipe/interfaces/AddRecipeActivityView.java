package com.makers.lemoncook.src.addRecipe.interfaces;

import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

public interface AddRecipeActivityView {
    void removeImage(int idx);

    void getRecipeSuccess(boolean isSuccess, int code, String message, ResponseRecipe.Result result);

    void getRecipeFailure();
}
