package com.makers.lemoncook.src.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.login.interfaces.LoginActivityView;
import com.makers.lemoncook.src.login.models.LoginRequest;
import com.makers.lemoncook.src.login.models.RequestShare;
import com.makers.lemoncook.src.main.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

import static com.makers.lemoncook.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makers.lemoncook.src.ApplicationClass.sSharedPreferences;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    CustomDialogGetRecipe mCustomDialogGetRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen(); //자동 로그인
    }

    @Override
    public void loginSuccess(boolean isSuccess, int code, String message, String jwt) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            if (getIntent().getData() != null) {
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.putString(X_ACCESS_TOKEN, jwt);
                System.out.println("jwt = " + jwt);
                editor.commit();

                mCustomDialogGetRecipe = new CustomDialogGetRecipe(this, getRecipePositiveListener, getRecipeNegativeListener);
                mCustomDialogGetRecipe.show();
            }
            else {
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.putString(X_ACCESS_TOKEN, jwt);
                System.out.println("jwt = " + jwt);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void loginFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void shareSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        showCustomToast(message);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void shareFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void login(String accessToken) {
        showProgressDialog();
        LoginService loginService = new LoginService(this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccess_token(accessToken);
        loginService.postLogin(loginRequest);
    }

    // 세션 콜백 구현
    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
            AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {

                }

                @Override
                public void onSuccess(AccessTokenInfoResponse result) {

                }
            });
            String token = Session.getCurrentSession().getAccessToken();
            System.out.println("access token : " + token);
            login(token);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            showCustomToast("로그인 실패");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    public void postShare(int recipeNo) {
        showProgressDialog();
        LoginService loginService = new LoginService(this);
        RequestShare requestShare = new RequestShare();
        requestShare.setRecipeNo(recipeNo);
        loginService.postShare(requestShare);
    }

    private View.OnClickListener getRecipePositiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            int recipeNo = Integer.parseInt(Objects.requireNonNull(getIntent().getData()).getQueryParameter("recipeNo"));
            System.out.println("레시피번호가 안뜬다고?");
            System.out.println("recipeNo = " + recipeNo);
            postShare(recipeNo);

            mCustomDialogGetRecipe.dismiss();
        }
    };

    private View.OnClickListener getRecipeNegativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            mCustomDialogGetRecipe.dismiss();
        }
    };
}
