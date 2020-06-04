package com.makers.lemoncook.src.login.models;

import com.google.gson.annotations.SerializedName;

public class RequestShare {
    @SerializedName("recipeNo")
    int recipeNo;

    public void setRecipeNo(int recipeNo) {
        this.recipeNo = recipeNo;
    }
}
