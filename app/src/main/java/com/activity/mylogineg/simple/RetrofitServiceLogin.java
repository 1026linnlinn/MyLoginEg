package com.activity.mylogineg.simple;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceLogin {
    public static final String BASE_URL="http://128.199.180.50";

    public Retrofit getService(){
        Retrofit build = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return build;
    }
}

