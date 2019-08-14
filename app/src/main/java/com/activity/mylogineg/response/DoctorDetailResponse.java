package com.activity.mylogineg.response;

import com.activity.mylogineg.model.DoctorDetail;
import com.google.gson.annotations.SerializedName;

public class DoctorDetailResponse {
    @SerializedName("is_success")
    public Boolean isSuccess;

    @SerializedName("doctor")
    public DoctorDetail doctorDetail;
}
