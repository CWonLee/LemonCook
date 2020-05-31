package com.makers.lemoncook.src.editRecipe.interfaces;

import com.makers.lemoncook.src.editRecipe.models.RequestPostRecipe;
import com.makers.lemoncook.src.editRecipe.models.ResponsePostRecipe;
import com.makers.lemoncook.src.editRecipe.models.ResponseUpload;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface EditRecipeRetrofitInterface {
    @POST("/recipes")
    Call<ResponsePostRecipe> postRecipe(@Body RequestPostRecipe params);

    @Multipart
    @POST("/upload")
    Call<ResponseUpload> postUpload(
            @Part MultipartBody.Part mainImage,
            @Part ArrayList<MultipartBody.Part> recipeImage
            );
}
