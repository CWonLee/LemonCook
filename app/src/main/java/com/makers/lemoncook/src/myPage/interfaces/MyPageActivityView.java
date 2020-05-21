package com.makers.lemoncook.src.myPage.interfaces;

import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;

import java.util.ArrayList;

public interface MyPageActivityView {
    void getMyPageSuccess(boolean isSuccess, int code, String message, ResponseGetMyPage.Result result);

    void getMyPageFailure();
}
