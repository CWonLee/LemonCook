package com.makers.lemoncook.src.myPage.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseGetMyPage {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private Result result;

    public class Result implements Serializable {
        @SerializedName("userNo")
        private int userNo;

        @SerializedName("nickname")
        private String nickname;

        @SerializedName("registerdRecipe")
        private int registerdRecipe;

        @SerializedName("sharedRecipe")
        private int sharedRecipe;

        @SerializedName("recipeInfo")
        private ArrayList<RecipeInfo> recipeInfo;

        public class RecipeInfo implements Serializable {
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
        }

        public int getUserNo() {
            return userNo;
        }

        public String getNickname() {
            return nickname;
        }

        public int getRegisterdRecipe() {
            return registerdRecipe;
        }

        public int getSharedRecipe() {
            return sharedRecipe;
        }

        public ArrayList<RecipeInfo> getRecipeInfo() {
            return recipeInfo;
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

    public Result getResult() {
        return result;
    }
}
