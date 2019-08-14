package com.activity.mylogineg.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doctor {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("towns")
    public List<String > towns;

    @SerializedName("photo")
    public String photo;

    @SerializedName("specialists")
    public List<String> specialists;

    @SerializedName("clinics")
    public List<String> clinics;
}
