package com.makers.lemoncook.src.editRecipe.models;

import com.google.gson.annotations.SerializedName;

import java.io.File;
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

    public static class CookingOrder {
        @SerializedName("cookingOrder")
        private int cookingOrder;

        @SerializedName("cookingOrderImage")
        private String cookingOrderImage;

        @SerializedName("content")
        private String content;

        public void setCookingOrder(int cookingOrder) {
            this.cookingOrder = cookingOrder;
        }

        public void setCookingOrderImage(String cookingOrderImage) {
            this.cookingOrderImage = cookingOrderImage;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public void setCookingOrder(ArrayList<CookingOrder> cookingOrder) {
        this.cookingOrder = cookingOrder;
    }
}
