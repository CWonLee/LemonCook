package com.makers.lemoncook.src.login.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {
    @SerializedName("result")
    private Result result;

    @SerializedName("jwt")
    private String jwt;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public class Result {
        @SerializedName("userNo")
        private int userNo;

        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        @SerializedName("nickname")
        private String nickname;

        @SerializedName("status")
        private int status;

        public int getUserNo() {
            return userNo;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getNickname() {
            return nickname;
        }

        public int getStatus() {
            return status;
        }
    }

    public Result getUserInfo() {
        return result;
    }

    public String getJwt() {
        return jwt;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
