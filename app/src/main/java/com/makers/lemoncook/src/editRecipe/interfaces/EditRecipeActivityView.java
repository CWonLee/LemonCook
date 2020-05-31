package com.makers.lemoncook.src.editRecipe.interfaces;

import com.makers.lemoncook.src.editRecipe.models.ResponseUpload;

public interface EditRecipeActivityView {
    void change(int idx);

    void getInterface(EditRecipeRecyclerViewAdapterInterface editRecipeRecyclerViewAdapterInterface);

    void postRecipeSuccess(boolean isSuccess, int code, String message);

    void postRecipeFailure();

    void uploadSuccess(boolean isSuccess, int code, String message, ResponseUpload.Result result);

    void uploadFailure();
}
