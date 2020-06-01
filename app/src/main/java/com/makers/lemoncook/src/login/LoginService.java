package com.makers.lemoncook.src.login;

import com.makers.lemoncook.src.login.interfaces.LoginActivityView;
import com.makers.lemoncook.src.login.interfaces.LoginRetrofitInterface;
import com.makers.lemoncook.src.login.models.LoginRequest;
import com.makers.lemoncook.src.login.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class LoginService {
    private final LoginActivityView mLoginActivityView;

    LoginService(final LoginActivityView loginActivityView) {
        this.mLoginActivityView = loginActivityView;
    }

    void postLogin(LoginRequest loginRequest) {
        final LoginRetrofitInterface loginRetrofitInterface = getRetrofit().create(LoginRetrofitInterface.class);
        loginRetrofitInterface.postLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                final LoginResponse loginResponse = response.body();
                mLoginActivityView.loginSuccess(loginResponse.isSuccess(), loginResponse.getCode(), loginResponse.getMessage(), loginResponse.getJwt());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                mLoginActivityView.loginFailure();
            }
        });
    }
}
