package com.makers.lemoncook.src.join;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.join.interfaces.JoinActivityView;
import com.makers.lemoncook.src.join.models.JoinRequest;
import com.makers.lemoncook.src.login.LoginActivity;

public class JoinActivity extends BaseActivity implements JoinActivityView {

    EditText mEtEmail, mEtNickname, mEtPassword, mEtPhoneNumber;
    Button mBtnDupCheck, mBtnCert, mBtnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEtEmail = findViewById(R.id.join_et_email);
        mEtNickname = findViewById(R.id.join_et_nickname);
        mEtPassword = findViewById(R.id.join_et_password);
        mEtPhoneNumber = findViewById(R.id.join_et_phone_cert);
        mBtnDupCheck = findViewById(R.id.join_btn_check_dup);
        mBtnCert = findViewById(R.id.join_btn_phone_cert);
        mBtnComplete = findViewById(R.id.join_btn_complete);

        mBtnDupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mBtnCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });
    }

    @Override
    public void joinSuccess(boolean isSuccess, int code, String message) {
        if (isSuccess) {
            showCustomToast(message);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void joinFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void join() {
        JoinService joinService = new JoinService(this);
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setEmail(mEtEmail.getText().toString());
        joinRequest.setNickname(mEtNickname.getText().toString());
        joinRequest.setPassword(mEtPassword.getText().toString());
        joinRequest.setPhone(mEtPhoneNumber.getText().toString());
        joinService.postJoin(joinRequest);
    }
}
