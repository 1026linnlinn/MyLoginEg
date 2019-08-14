package com.activity.mylogineg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.activity.mylogineg.R;
import com.activity.mylogineg.adapter.DoctorAdapter;
import com.activity.mylogineg.api.ApiInterface;
import com.activity.mylogineg.holder.DoctorHolder;
import com.activity.mylogineg.model.Doctor;
import com.activity.mylogineg.response.DoctorListResponse;
import com.activity.mylogineg.simple.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity implements DoctorHolder.OnDoctorClickListener {

    private RecyclerView recyclerView;
    private Spinner spinner;

    private RetrofitService service;

    DoctorAdapter adapter;

    List<Doctor> doctors = new ArrayList<>();

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDoctorList();
        getDoctorsList();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initDoctorList() {
        setContentView(R.layout.activity_doctor_list);

        Bundle bundle = getIntent().getExtras();//getting extra form login activity as token
        token = bundle.getString("Token");
        Log.e("doctorlisttoken:", token);//to take token from doctorListApi,doctor

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //  recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);
        service = new RetrofitService();
        adapter = new DoctorAdapter(this);

        List<String> catagories = new ArrayList<String>();
        catagories.add("Dentist");
        catagories.add("OG");
        catagories.add("Endocrinologists");
        catagories.add("Cardiologist");
        catagories.add("Addiction");
        catagories.add("Simple");
        catagories.add("General");

        //     doctor.add(Log.e("Doctor List:",));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, catagories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //setContentView(R.layout.item);
        // getDoctorsList();

    }

    private void getDoctorsList() {

        ApiInterface doctorListApi = service.getService().create(ApiInterface.class);
        doctorListApi.doctor(token).enqueue(new Callback<DoctorListResponse>() {
            @Override
            public void onResponse(Call<DoctorListResponse> call, Response<DoctorListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        doctors = response.body().doctorLists.doctors;
                        adapter.addDoctors(doctors);
                        Log.e("DoctorListWithNumber", String.valueOf(doctors.size()));//to show the token from adapter

                    }
                }
            }

            @Override
            public void onFailure(Call<DoctorListResponse> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }

        });
    }

    @Override
    public void onDoctorClick(int id) {
        Intent intent = new Intent(getApplicationContext(), DoctorDetailActivity.class);
        intent.putExtra("doctorId", id);
        intent.putExtra("Token", token);
        Log.e("doctor_id_now", String.valueOf(id));
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
}
