package com.activity.mylogineg.api;

import com.activity.mylogineg.response.DoctorDetailResponse;
import com.activity.mylogineg.response.DoctorListResponse;
import com.activity.mylogineg.response.LoginResponse;
import com.activity.mylogineg.response.RegisterResponse;
import com.activity.mylogineg.response.SpecializationListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded // annotation used in POST type requests
    @POST("/api/register")     // API's endpoints
    Call<RegisterResponse> register(@Field("phoneNumber") long phoneNumber,
                                     @Field("password") String password ,
                                     @Field("password_confirmation") String passwordConfirm);

    @FormUrlEncoded
    @POST("/api/login")
    Call<LoginResponse> login(@Field("phoneNumber") long phoneNumber,
                              @Field("password") String password );

    @FormUrlEncoded
    @POST("/api/doctor_list")
    Call<DoctorListResponse> doctor(@Field("token")String token);

    @FormUrlEncoded
    @POST("api/doctor_details")
    Call<DoctorDetailResponse> getDoctorDetail(@Field("token") String token,
                                               @Field("doctor_id") int id);

    @FormUrlEncoded
    @POST("api/doctor_list_by_specialization")
    Call<DoctorListResponse> getDoctorList(@Field("token") String token ,
                                           @Field("specialization_id") int id);

    @FormUrlEncoded
    @POST("api/specialization_list")
    Call<SpecializationListResponse> getSpecializationList(@Field("token") String token);

}
