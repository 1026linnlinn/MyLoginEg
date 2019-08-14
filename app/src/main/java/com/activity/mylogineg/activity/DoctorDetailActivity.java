package com.activity.mylogineg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.activity.mylogineg.R;
import com.activity.mylogineg.api.ApiInterface;
import com.activity.mylogineg.response.DoctorDetailResponse;
import com.activity.mylogineg.service.Token;
import com.activity.mylogineg.simple.RetrofitService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailActivity extends AppCompatActivity {

    private RetrofitService service;
    private String token;
    private Button button;
    private de.hdodenhof.circleimageview.CircleImageView imageView;
    private TextView tvName, tvType, about, tvclinic, tvtown, tvSpecialists;
    private int doctorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor);
        initActivity();
    }

    private void initActivity() {

        service = new RetrofitService();
        token = Token.MyToken.getToken();

        tvName = findViewById(R.id.tvName);
        tvType = findViewById(R.id.tvType);
        imageView = findViewById(R.id.profile);
        button=findViewById(R.id.pagerHeader);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("login:token", "success");
                startActivity(new Intent(getApplicationContext(), DoctorListActivity.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        doctorId = bundle.getInt("doctorId");
        token = bundle.getString("Token");
        Log.e("doctorId", String.valueOf(doctorId));

        about = findViewById(R.id.about);
        tvclinic = findViewById(R.id.clinic);
        tvtown = findViewById(R.id.town);
        tvSpecialists = findViewById(R.id.specialist);

        getDoctorDetail();

    }

    private void getDoctorDetail() {

        ApiInterface doctorDetailApi = service.getService().create(ApiInterface.class);
        doctorDetailApi.getDoctorDetail(token, doctorId).enqueue(new Callback<DoctorDetailResponse>() {
            @Override
            public void onResponse(Call<DoctorDetailResponse> call, Response<DoctorDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        Picasso.get()
                                .load("http://128.199.180.50/api/get_image/" + response.body().doctorDetail.photo)
                                .resize(40, 40)
                                .onlyScaleDown()
                                .centerCrop()
                                .into(imageView);

                        String specialists = response.body().doctorDetail.specialists.get(0);
                        String clinics = response.body().doctorDetail.clinics.get(0);
                        String towns = response.body().doctorDetail.towns.get(0);

                        for (int i = 1; i < response.body().doctorDetail.specialists.size(); i++) {
                            specialists += "," + response.body().doctorDetail.specialists.get(i);
                        }


                        for (int i = 1; i < response.body().doctorDetail.clinics.size(); i++) {
                            clinics += "," + response.body().doctorDetail.clinics.get(i);
                        }


                        for (int i = 1; i < response.body().doctorDetail.towns.size(); i++) {
                            towns += "," + response.body().doctorDetail.towns.get(i);
                        }

                        about.setText(response.body().doctorDetail.about);
                        tvSpecialists.setText(specialists);
                        tvclinic.setText(clinics);
                        tvtown.setText(towns);
                        tvName.setText(response.body().doctorDetail.name);

                        Log.e("getDoctorDetail", "success");

                    }
                }
            }

            @Override
            public void onFailure(Call<DoctorDetailResponse> call, Throwable t) {

            }
        });


    }
}
