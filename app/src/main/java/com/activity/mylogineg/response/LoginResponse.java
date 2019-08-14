package com.activity.mylogineg.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    public String token;

    @SerializedName("is_success")
    public Boolean is_success;

}
