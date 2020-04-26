package com.makers.lemoncook.src.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.join.JoinActivity;
import com.makers.lemoncook.src.login.interfaces.LoginActivityView;
import com.makers.lemoncook.src.login.models.LoginRequest;
import com.makers.lemoncook.src.login.models.LoginResponse;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    EditText mEtEmail, mEtPassword;
    Button mBtnLogin;
    TextView mTvJoin, mTvFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtEmail = findViewById(R.id.login_et_email);
        mEtPassword = findViewById(R.id.login_et_password);
        mBtnLogin = findViewById(R.id.login_btn_login);
        mTvJoin = findViewById(R.id.login_tv_join);
        mTvFind = findViewById(R.id.login_tv_find);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mTvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
        mTvFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void loginSuccess(LoginResponse.UserInfo userInfo, String jwt, boolean isSuccess, int code, String message) {
        showCustomToast(message);
    }

    @Override
    public void loginFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void login() {
        LoginService loginService = new LoginService(this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(mEtEmail.getText().toString());
        loginRequest.setPassword(mEtPassword.getText().toString());
        loginService.postLogin(loginRequest);
    }
}
