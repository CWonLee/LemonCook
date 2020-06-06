package com.makers.lemoncook.src.setting.interfaces;

import com.makers.lemoncook.src.setting.models.ResponseDeleteUser;

import retrofit2.Call;
import retrofit2.http.DELETE;

public interface SettingRetrofitInterface {
    @DELETE("/secession")
    Call<ResponseDeleteUser> deleteUser();
}
