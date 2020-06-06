package com.makers.lemoncook.src.modifyRecipe;

import com.makers.lemoncook.src.addRecipe.interfaces.AddRecipeRetrofitInterface;
import com.makers.lemoncook.src.addRecipe.models.ResponseGetMaterial;
import com.makers.lemoncook.src.modifyRecipe.interfaces.ModifyActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class ModifyService {
    private final ModifyActivityView mModifyActivityView;

    ModifyService(final ModifyActivityView modifyActivityView) {
        this.mModifyActivityView = modifyActivityView;
    }

    void getMaterial(String search) {
        final AddRecipeRetrofitInterface addRecipeRetrofitInterface = getRetrofit().create(AddRecipeRetrofitInterface.class);
        addRecipeRetrofitInterface.getMaterial(search).enqueue(new Callback<ResponseGetMaterial>() {
            @Override
            public void onResponse(Call<ResponseGetMaterial> call, Response<ResponseGetMaterial> response) {
                final ResponseGetMaterial responseGetMaterial = response.body();
                mModifyActivityView.getMaterialSuccess(responseGetMaterial.isSuccess(), responseGetMaterial.getCode(), responseGetMaterial.getMessage(), responseGetMaterial.getResult());
            }

            @Override
            public void onFailure(Call<ResponseGetMaterial> call, Throwable t) {
                t.printStackTrace();
                mModifyActivityView.getMaterialFailure();
            }
        });
    }
}
