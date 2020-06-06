package com.makers.lemoncook.src.setting;

import com.makers.lemoncook.src.setting.interfaces.SettingActivityView;
import com.makers.lemoncook.src.setting.interfaces.SettingRetrofitInterface;
import com.makers.lemoncook.src.setting.models.ResponseDeleteUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class SettingService {
    private final SettingActivityView mSettingActivityView;

    SettingService(final SettingActivityView settingActivityView) {
        this.mSettingActivityView = settingActivityView;
    }

    void deleteUser() {
        final SettingRetrofitInterface settingRetrofitInterface = getRetrofit().create(SettingRetrofitInterface.class);
        settingRetrofitInterface.deleteUser().enqueue(new Callback<ResponseDeleteUser>() {
            @Override
            public void onResponse(Call<ResponseDeleteUser> call, Response<ResponseDeleteUser> response) {
                final ResponseDeleteUser responseDeleteUser = response.body();
                mSettingActivityView.deleteUserSuccess(responseDeleteUser.isSuccess(), responseDeleteUser.getCode(), responseDeleteUser.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseDeleteUser> call, Throwable t) {
                t.printStackTrace();
                mSettingActivityView.deleteUserFailure();
            }
        });
    }
}
