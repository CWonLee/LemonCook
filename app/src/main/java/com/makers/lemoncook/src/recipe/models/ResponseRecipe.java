package com.makers.lemoncook.src.recipe.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseRecipe {
    @SerializedName("result")
    private Result result;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public static class Result implements Serializable {
        @SerializedName("recipeNo")
        private int recipeNo;

        @SerializedName("userNo")
        private int userNo;

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
        private ArrayList<Ingredient> ingredient;

        @SerializedName("serving")
        private String serving;

        @SerializedName("isSave")
        private int isSave;

        @SerializedName("cookingOrder")
        private ArrayList<CookingOrder> cookingOrder;

        public class Ingredient implements Serializable {
            @SerializedName("ingredient")
            private String ingredient;

            public String getIngredient() {
                return ingredient;
            }
        }

        public class CookingOrder {
            @SerializedName("cookingOrder")
            private int cookingOrder;

            @SerializedName("cookingOrderImage")
            private String cookingOrderImage;

            @SerializedName("content")
            private String content;

            public int getCookingOrder() {
                return cookingOrder;
            }

            public String getCookingOrderImage() {
                return cookingOrderImage;
            }

            public String getContent() {
                return content;
            }
        }

        public int getRecipeNo() {
            return recipeNo;
        }

        public int getUserNo() {
            return userNo;
        }

        public int getCategoryNo() {
            return categoryNo;
        }

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getName() {
            return name;
        }

        public String getHashTag() {
            return hashTag;
        }

        public ArrayList<Ingredient> getIngredient() {
            return ingredient;
        }

        public String getServing() {
            return serving;
        }

        public int getIsSave() {
            return isSave;
        }

        public ArrayList<CookingOrder> getCookingOrder() {
            return cookingOrder;
        }
    }

    public Result getResult() {
        return result;
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
}
