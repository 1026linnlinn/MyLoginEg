package com.activity.mylogineg.response;

import com.activity.mylogineg.model.SpecializationList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecializationListResponse extends SpecializationList {
    @SerializedName("is_success")
    public Boolean isSuccess;

    @SerializedName("specialists")
    public List<SpecializationList> specializations;
}
