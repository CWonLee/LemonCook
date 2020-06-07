package com.makers.lemoncook.src;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.makers.lemoncook.R;

public class PrivacyPolicy extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        mWebView = findViewById(R.id.privacy_policy_wb);

        mWebView.setWebViewClient(new WebViewClient());
        String url = "http://3.34.87.181/%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8/lemoncook%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8.html";
        mWebView.loadUrl(url);
    }
}
