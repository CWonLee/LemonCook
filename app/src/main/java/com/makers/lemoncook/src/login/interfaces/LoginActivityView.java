package com.makers.lemoncook.src.login.interfaces;

import com.makers.lemoncook.src.login.models.LoginResponse;

public interface LoginActivityView {
    void loginSuccess(boolean isSuccess, int code, String message, String jwt);

    void loginFailure();

    void shareSuccess(boolean isSuccess, int code, String message);

    void shareFailure();
}
