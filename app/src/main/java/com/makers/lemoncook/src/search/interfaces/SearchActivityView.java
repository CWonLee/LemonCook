package com.makers.lemoncook.src.search.interfaces;

import com.makers.lemoncook.src.search.models.ResponseSearch;

import java.util.ArrayList;

public interface SearchActivityView {
    void searchSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseSearch.Result> result);

    void searchFailure();
}
