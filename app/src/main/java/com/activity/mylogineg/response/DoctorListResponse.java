package com.activity.mylogineg.response;

import com.activity.mylogineg.model.DoctorList;
import com.google.gson.annotations.SerializedName;

public class DoctorListResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("doctors")
    public DoctorList doctorLists;
}
