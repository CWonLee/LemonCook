package com.makers.lemoncook.src.setting.interfaces;

public interface SettingActivityView {
    void deleteUserSuccess(boolean isSuccess, int code, String message);

    void deleteUserFailure();
}
