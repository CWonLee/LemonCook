package com.makers.lemoncook.src.loadRecipe;

import com.makers.lemoncook.src.loadRecipe.interfaces.LoadActivityView;
import com.makers.lemoncook.src.recipe.interfaces.RecipeRetrofitInterface;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;
import com.makers.lemoncook.src.search.interfaces.SearchRetrofitInterface;
import com.makers.lemoncook.src.search.models.ResponseSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class LoadService {
    private final LoadActivityView mLoadActivityView;

    LoadService(final LoadActivityView loadActivityView) {
        this.mLoadActivityView = loadActivityView;
    }

    void getSearch(String search, String filter, String sort, int page, final boolean clearData) {
        final SearchRetrofitInterface searchRetrofitInterface = getRetrofit().create(SearchRetrofitInterface.class);
        searchRetrofitInterface.getSearch(search, filter, sort, page).enqueue(new Callback<ResponseSearch>() {
            @Override
            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
                final ResponseSearch responseSearch = response.body();
                mLoadActivityView.searchSuccess(responseSearch.isSuccess(), responseSearch.getCode(), responseSearch.getMessage(), responseSearch.getResult(), clearData);
            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {
                t.printStackTrace();
                mLoadActivityView.searchFailure();
            }
        });
    }
}
