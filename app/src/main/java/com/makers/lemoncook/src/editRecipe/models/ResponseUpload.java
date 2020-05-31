package com.makers.lemoncook.src.editRecipe.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseUpload {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private Result result;

    public class Result {
        @SerializedName("image")
        private ImageClass image;

        @SerializedName("cookingOrderImage")
        private ArrayList<ImageClass> cookingOrderImage;

        public class ImageClass {
            @SerializedName("imageUrl")
            private String imageUrl;

            public String getImageUrl() {
                return imageUrl;
            }
        }

        public ImageClass getImage() {
            return image;
        }

        public ArrayList<ImageClass> getCookingOrderImage() {
            return cookingOrderImage;
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
