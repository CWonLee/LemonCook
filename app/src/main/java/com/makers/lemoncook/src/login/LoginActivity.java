package com.makers.lemoncook.src.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.join.JoinActivity;
import com.makers.lemoncook.src.login.interfaces.LoginActivityView;
import com.makers.lemoncook.src.login.models.LoginRequest;
import com.makers.lemoncook.src.login.models.LoginResponse;
import com.makers.lemoncook.src.main.MainActivity;

import java.util.ArrayList;

import static com.makers.lemoncook.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makers.lemoncook.src.ApplicationClass.sSharedPreferences;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    EditText mEtEmail, mEtPassword;
    Button mBtnLogin;
    TextView mTvJoin, mTvFind;
    ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mConstraintLayout = findViewById(R.id.login_cl);
        mEtEmail = findViewById(R.id.login_et_email);
        mEtPassword = findViewById(R.id.login_et_password);
        mBtnLogin = findViewById(R.id.login_btn_login);
        mTvJoin = findViewById(R.id.login_tv_join);
        mTvFind = findViewById(R.id.login_tv_find);

        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });
        mBtnLogin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                login();
            }
        });
        mTvJoin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
        mTvFind.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });
    }

    @Override
    public void loginSuccess(LoginResponse.Result userInfo, String jwt, boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.putString(X_ACCESS_TOKEN, jwt);
            System.out.println("jwt = " + jwt);
            editor.putInt("userNo", userInfo.getUserNo());
            editor.putString("email", userInfo.getEmail());
            editor.putString("password", userInfo.getPassword());
            editor.putString("nickname", userInfo.getPassword());
            editor.putInt("status", userInfo.getStatus());
            editor.commit();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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

    public void login() {
        showProgressDialog();
        LoginService loginService = new LoginService(this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(mEtEmail.getText().toString());
        loginRequest.setPassword(mEtPassword.getText().toString());
        loginService.postLogin(loginRequest);
    }
}
