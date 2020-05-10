package com.makers.lemoncook.src.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.login.LoginActivity;
import com.makers.lemoncook.src.main.MainActivity;

import static com.makers.lemoncook.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makers.lemoncook.src.ApplicationClass.sSharedPreferences;

public class SplashActivity extends BaseActivity {

    ImageView mIvNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mIvNextButton = findViewById(R.id.splash_iv_next_btn);

        mIvNextButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                String jwt = sSharedPreferences.getString(X_ACCESS_TOKEN, null);
                if (jwt == null) {
                    startActivity(new Intent(getApplication(), LoginActivity.class));
                    SplashActivity.this.finish();
                }
                else {
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    SplashActivity.this.finish();
                }
            }
        });
    }
}
