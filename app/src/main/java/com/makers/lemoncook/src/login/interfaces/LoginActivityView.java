package com.makers.lemoncook.src.login.interfaces;

import com.makers.lemoncook.src.login.models.LoginResponse;

public interface LoginActivityView {
    void loginSuccess(LoginResponse.Result userInfo, String jwt, boolean isSuccess, int code, String message);

    void loginFailure();
}
