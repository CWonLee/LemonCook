package com.makers.lemoncook.src.recipeList.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseGetRecipe {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<Result> result;

    public class Result implements Serializable {
        @SerializedName("recipeNo")
        private int recipeNo;

        @SerializedName("recipeImage")
        private String recipeImage;

        @SerializedName("recipeTilte")
        private String recipeTilte;

        @SerializedName("recipeName")
        private String recipeName;

        @SerializedName("recipeHashTag")
        private String recipeHashTag;

        @SerializedName("recipeCreatedAt")
        private String recipeCreatedAt;

        @SerializedName("isSave")
        private int isSave;

        public int getRecipeNo() {
            return recipeNo;
        }

        public String getRecipeImage() {
            return recipeImage;
        }

        public String getRecipeTilte() {
            return recipeTilte;
        }

        public String getRecipeName() {
            return recipeName;
        }

        public String getRecipeHashTag() {
            return recipeHashTag;
        }

        public String getRecipeCreatedAt() {
            return recipeCreatedAt;
        }

        public int getIsSave() {
            return isSave;
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }
}
