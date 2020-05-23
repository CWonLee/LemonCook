package com.makers.lemoncook.src.editRecipe.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestPostRecipe {
    @SerializedName("categoryNo")
    private int categoryNo;

    @SerializedName("image")
    private String image;

    @SerializedName("title")
    private String title;

    @SerializedName("name")
    private String name;

    @SerializedName("hashTag")
    private String hashTag;

    @SerializedName("ingredient")
    private String ingredient;

    @SerializedName("serving")
    private String serving;

    @SerializedName("cookingOrder")
    private ArrayList<CookingOrder> cookingOrder;

    public class CookingOrder {
        @SerializedName("cookingOrder")
        private int cookingOrder;

        @SerializedName("cookingOrderImage")
        private String cookingOrderImage;

        @SerializedName("content")
        private String content;
    }
}
