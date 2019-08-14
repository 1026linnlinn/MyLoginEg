package com.activity.mylogineg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.activity.mylogineg.R;
import com.activity.mylogineg.adapter.DoctorAdapter;
import com.activity.mylogineg.api.ApiInterface;
import com.activity.mylogineg.holder.DoctorHolder;
import com.activity.mylogineg.model.Doctor;
import com.activity.mylogineg.model.SpecializationList;
import com.activity.mylogineg.response.DoctorListResponse;
import com.activity.mylogineg.response.SpecializationListResponse;
import com.activity.mylogineg.service.Token;
import com.activity.mylogineg.simple.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DoctorHolder.OnDoctorClickListener, Spinner.OnClickListener {

    private Intent intent;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private RetrofitService service;
    private Spinner spinner;
    DoctorAdapter adapter;
    ArrayAdapter<String> dataAdapter;
    List<com.activity.mylogineg.model.SpecializationList> specializationLists = new ArrayList<>();
    List<String> categories = new ArrayList<>();
    List<Doctor> doctors = new ArrayList<>();
    List<Doctor> newDoctors = new ArrayList<>();
    private String token = null;
    private int specializationId = -1;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initDoctorList();
        getDoctorList();
//        searchViewFilter();
        searchViewModify();
    }

    private void getDoctorList() {
        ApiInterface doctorListApi = service.getService().create(ApiInterface.class);
        doctorListApi.doctor(token).enqueue(new Callback<DoctorListResponse>() {
            @Override
            public void onResponse(Call<DoctorListResponse> call, Response<DoctorListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        doctors = response.body().doctorLists.doctors;
                        adapter.addDoctors(doctors);

                        Log.e("DoctorLists", String.valueOf(doctors.size()));
                    }
                }
            }

            @Override
            public void onFailure(Call<DoctorListResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.search_view_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initDoctorList() {
        searchView = findViewById(R.id.sv);
        recyclerView = findViewById(R.id.recyclerView);
        service = new RetrofitService();
        adapter = new DoctorAdapter(this);
        spinner = findViewById(R.id.spinner);

        token = Token.MyToken.getToken();
        Log.e("DrawerActivityToken", token);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        dataAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        getSpecializationList(token);

        Bundle bundle = getIntent().getExtras();
        specializationId = bundle.getInt("specializationId");

        if (specializationId != -1) {
            getDoctorsBySpecialization(specializationId);
            Log.e("specializationId", String.valueOf(specializationId));
        }

    }

    private void getSpecializationList(String token) {
        ApiInterface specializationListApi = service.getService().create(ApiInterface.class);

        specializationListApi.getSpecializationList(token).enqueue(new Callback<SpecializationListResponse>() {
            @Override
            public void onResponse(Call<SpecializationListResponse> call, Response<SpecializationListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        specializationLists = response.body().specializations;
                        Log.e("Special size", String.valueOf(response.body().specializations.size()));

                        for (SpecializationList special : specializationLists) {
                            categories.add(special.name);
                            Log.e("name", special.name);
                        }
                        categories.add(0, "ALL");
                        dataAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<SpecializationListResponse> call, Throwable t) {

            }


        });
    }

    //    private void searchViewFilter() {
//
//        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//
//                s = s.toLowerCase(Locale.getDefault());
//                if (s.length() != 0) {
//                    newDoctors.clear();
//                    for (Doctor doctor : doctors) {
//                        if (doctor.name.toLowerCase(Locale.getDefault()).contains(s)) {
//
//                            newDoctors.add(doctor);
//                        }
//                    }
//                    adapter.addDoctors(newDoctors);
//                } else {
//                    adapter.addDoctors(doctors);
//                }
//                Toast.makeText(DrawerActivity.this, s, Toast.LENGTH_LONG).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//
////            @Override
////            public boolean onQueryTextChange(String s) {
////
////                s = s.toLowerCase(Locale.getDefault());
////                if (s.length() != 0) {
////                    newDoctors.clear();
////                    for (Doctor doctor : doctors) {
////                        if (doctor.name.toLowerCase(Locale.getDefault()).contains(s)) {
////
////                            newDoctors.add(doctor);
////                        }
////                    }
////                    adapter.addDoctors(newDoctors);
////                } else {
////                    adapter.addDoctors(doctors);
////                }
////
////                Toast.makeText(DrawerActivity.this, s, Toast.LENGTH_LONG).show();
////                return false;
////            }
//        });
//    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void searchViewModify() {
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHint("Search Doctors");
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.focusSearch(View.FOCUS_RIGHT);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDoctorClick(int id) {

        Intent intent = new Intent(getApplicationContext(), ThatIsJustAnActivity.class);//DoctorDetailItem|| DoctorDetailActivity
        intent.putExtra("doctorId", id);
        intent.putExtra("Token", token);
        Log.e("doctor_id", String.valueOf(id));
        startActivity(intent);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String name = spinner.getSelectedItem().toString();
        if (name.equals("All")) {
            adapter.addDoctors(doctors);
        } else {

            int special_id = 0;
            Log.e("spinner selected item", name);
            for (SpecializationList specializationList : specializationLists) {
                if (specializationList.name.equals(name)) {
                    special_id = specializationList.id;
                    Log.e("special_id", String.valueOf(id));
                }
            }
            getDoctorsBySpecialization(special_id);
        }
    }


    private void getDoctorsBySpecialization(int special_id) {
        ApiInterface doctorsBySpecialApi = service.getService().create(ApiInterface.class);
        doctorsBySpecialApi.getDoctorList(token, special_id).enqueue(new Callback<DoctorListResponse>() {
            @Override
            public void onResponse(Call<DoctorListResponse> call, Response<DoctorListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        adapter.addDoctors(response.body().doctorLists.doctors);
                        Log.e("DoctorLists", String.valueOf(doctors.size()));

                    }
                }
            }

            @Override
            public void onFailure(Call<DoctorListResponse> call, Throwable t) {

            }

        });
    }
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
