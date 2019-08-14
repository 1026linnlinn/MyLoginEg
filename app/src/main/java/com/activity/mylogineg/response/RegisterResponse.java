package com.activity.mylogineg.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("error_message")
    public String errorMessage;

    @SerializedName("token")
    public String token;
}
