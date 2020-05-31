package com.makers.lemoncook.src.recipeList.models;

import com.google.gson.annotations.SerializedName;

public class RequestPostZZim {
    @SerializedName("recipeNo")
    private int recipeNo;

    public void setRecipeNo(int recipeNo) {
        this.recipeNo = recipeNo;
    }
}
