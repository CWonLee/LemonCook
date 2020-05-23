package com.makers.lemoncook.src.search;

import com.makers.lemoncook.src.search.interfaces.SearchActivityView;
import com.makers.lemoncook.src.search.interfaces.SearchRetrofitInterface;
import com.makers.lemoncook.src.search.models.ResponseSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class SearchService {
    private final SearchActivityView mSearchActivityView;

    SearchService(final SearchActivityView searchActivityView) {
        this.mSearchActivityView = searchActivityView;
    }

    void getSearch(String search, String filter, String sort) {
        final SearchRetrofitInterface searchRetrofitInterface = getRetrofit().create(SearchRetrofitInterface.class);
        searchRetrofitInterface.getSearch(search, filter, sort).enqueue(new Callback<ResponseSearch>() {
            @Override
            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
                final ResponseSearch responseSearch = response.body();
                mSearchActivityView.searchSuccess(responseSearch.isSuccess(), responseSearch.getCode(), responseSearch.getMessage(), responseSearch.getResult());
            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {
                t.printStackTrace();
                mSearchActivityView.searchFailure();
            }
        });
    }
}
