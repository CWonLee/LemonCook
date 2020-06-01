package com.makers.lemoncook.src.recipe.models;

import com.google.gson.annotations.SerializedName;

public class RequestZZim {
    @SerializedName("recipeNo")
    private int recipeNo;

    public void setRecipeNo(int recipeNo) {
        this.recipeNo = recipeNo;
    }
}
