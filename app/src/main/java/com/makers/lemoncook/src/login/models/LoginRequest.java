package com.makers.lemoncook.src.login.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("access_token")
    private String access_token;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
