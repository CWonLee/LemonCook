package com.makers.lemoncook.src.modifyRecipe.interfaces;

public interface ModifyActivityView {
    void removeImage(int idx);

    void getMaterialSuccess(boolean isSuccess, int code, String message, String result);

    void getMaterialFailure();
}
