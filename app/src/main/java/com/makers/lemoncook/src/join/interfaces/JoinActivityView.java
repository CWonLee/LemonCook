package com.makers.lemoncook.src.join.interfaces;

public interface JoinActivityView {
    void joinSuccess(boolean isSuccess, int code, String message);

    void joinFailure();
}
