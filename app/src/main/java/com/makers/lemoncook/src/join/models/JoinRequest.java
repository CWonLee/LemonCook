package com.makers.lemoncook.src.join.models;

import com.google.gson.annotations.SerializedName;

public class JoinRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("phone")
    private String phone;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
