package com.makers.lemoncook.src.editRecipe;

import android.util.Log;

import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeRetrofitInterface;
import com.makers.lemoncook.src.editRecipe.models.RequestModifyRecipe;
import com.makers.lemoncook.src.editRecipe.models.RequestPostRecipe;
import com.makers.lemoncook.src.editRecipe.models.ResponseModifyRecipe;
import com.makers.lemoncook.src.editRecipe.models.ResponsePostRecipe;
import com.makers.lemoncook.src.editRecipe.models.ResponseUpload;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class EditRecipeService {
    private final EditRecipeActivityView mEditRecipeActivityView;

    EditRecipeService(final EditRecipeActivityView editRecipeActivityView) {
        this.mEditRecipeActivityView = editRecipeActivityView;
    }

    void postRecipe(RequestPostRecipe requestPostRecipe) {
        final EditRecipeRetrofitInterface editRecipeRetrofitInterface = getRetrofit().create(EditRecipeRetrofitInterface.class);
        editRecipeRetrofitInterface.postRecipe(requestPostRecipe).enqueue(new Callback<ResponsePostRecipe>() {
            @Override
            public void onResponse(Call<ResponsePostRecipe> call, Response<ResponsePostRecipe> response) {
                final ResponsePostRecipe responsePostRecipe = response.body();
                mEditRecipeActivityView.postRecipeSuccess(responsePostRecipe.isSuccess(), responsePostRecipe.getCode(), responsePostRecipe.getMessage());
            }

            @Override
            public void onFailure(Call<ResponsePostRecipe> call, Throwable t) {
                mEditRecipeActivityView.postRecipeFailure();
            }
        });
    }

    void uploadImage(MultipartBody.Part mainImage, ArrayList<MultipartBody.Part> recipeImage) {
        final EditRecipeRetrofitInterface editRecipeRetrofitInterface = getRetrofit().create(EditRecipeRetrofitInterface.class);
        editRecipeRetrofitInterface.postUpload(mainImage, recipeImage).enqueue(new Callback<ResponseUpload>() {
            @Override
            public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {
                if(response.isSuccessful()){
                    final ResponseUpload responseUpload = response.body();
                    mEditRecipeActivityView.uploadSuccess(responseUpload.isSuccess(), responseUpload.getCode(), responseUpload.getMessage(), responseUpload.getResult());
                }else{
                    System.out.println("uploadImage : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseUpload> call, Throwable t) {
                Log.e("uploadImage","onFailure : "+t.getLocalizedMessage());
                mEditRecipeActivityView.uploadFailure();
                t.printStackTrace();
            }
        });
    }

    void modifyRecipe(int recipeNo, RequestModifyRecipe requestModifyRecipe) {
        final EditRecipeRetrofitInterface editRecipeRetrofitInterface = getRetrofit().create(EditRecipeRetrofitInterface.class);

        editRecipeRetrofitInterface.modifyRecipe(recipeNo, requestModifyRecipe).enqueue(new Callback<ResponseModifyRecipe>() {
            @Override
            public void onResponse(Call<ResponseModifyRecipe> call, Response<ResponseModifyRecipe> response) {
                final ResponseModifyRecipe modifyRecipe = response.body();
                mEditRecipeActivityView.modifyRecipeSuccess(modifyRecipe.isSuccess(), modifyRecipe.getCode(), modifyRecipe.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseModifyRecipe> call, Throwable t) {
                mEditRecipeActivityView.modifyRecipeFailure();
                t.printStackTrace();
            }
        });
    }
}
