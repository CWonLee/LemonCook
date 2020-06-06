package com.makers.lemoncook.src.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.login.LoginActivity;
import com.makers.lemoncook.src.setting.interfaces.SettingActivityView;
import com.makers.lemoncook.src.splash.SplashActivity;

import static com.makers.lemoncook.src.ApplicationClass.sSharedPreferences;

public class SettingActivity extends BaseActivity implements SettingActivityView {

    TextView mTvName;
    Button mBtnLogout, mBtnDeleteUser;
    CustomDialogLogout mCustomDialogLogout;
    CustomDialogDeleteUser mCustomDialogDeleteUser;
    ImageView mIvBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mTvName = findViewById(R.id.setting_tv_user_name);
        mBtnLogout = findViewById(R.id.setting_btn_logout);
        mBtnDeleteUser = findViewById(R.id.setting_btn_delete_user);
        mIvBackBtn = findViewById(R.id.setting_iv_back);

        mTvName.setText(getIntent().getStringExtra("name"));
        mBtnLogout.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mCustomDialogLogout = new CustomDialogLogout(SettingActivity.this,positiveListener,negativeListener);
                mCustomDialogLogout.show();
            }
        });
        mIvBackBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mBtnDeleteUser.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mCustomDialogDeleteUser = new CustomDialogDeleteUser(SettingActivity.this, deleteUserPositiveListener, deleteUserNegativeListener);
                mCustomDialogDeleteUser.show();
            }
        });
    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {

            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                    showCustomToast("로그아웃 되었습니다");
                }
            });
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(SettingActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            mCustomDialogLogout.dismiss();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialogLogout.dismiss();
        }
    };

    private View.OnClickListener deleteUserPositiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            deleteUser();

            mCustomDialogDeleteUser.dismiss();
        }
    };

    private View.OnClickListener deleteUserNegativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialogDeleteUser.dismiss();
        }
    };

    public void deleteUser() {
        showProgressDialog();
        SettingService settingService = new SettingService(this);
        settingService.deleteUser();
    }

    @Override
    public void deleteUserSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);

            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {

                }
            });

            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(SettingActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void deleteUserFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }
}
