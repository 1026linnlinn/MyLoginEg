package com.activity.mylogineg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activity.mylogineg.R;
import com.activity.mylogineg.api.ApiInterface;
import com.activity.mylogineg.response.RegisterResponse;
import com.activity.mylogineg.simple.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText edtPhoneNumberRegister, edtPasswordRegister, edtPasswordConfirmRegister;
    private Button btnRegister, btnToLogin;
    private RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        service = new RetrofitService();

        edtPhoneNumberRegister = findViewById(R.id.edtPhoneNumber);
        edtPasswordRegister = findViewById(R.id.edtPassword);
        edtPasswordConfirmRegister = findViewById(R.id.edtPasswordConfirm);

        btnRegister = findViewById(R.id.btnRegister);
        btnToLogin = findViewById(R.id.btnToLogin);

        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.btnToLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                register(Long.parseLong(edtPhoneNumberRegister.getText().toString()), edtPasswordRegister.getText().toString(), edtPasswordConfirmRegister.getText().toString());
                break;
            case R.id.btnToLogin:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    private void register(long phone, String pass, String passConfirm) {

        ApiInterface apiInterface;
        apiInterface = service.getService().create(ApiInterface.class);

        apiInterface.register(phone, pass, passConfirm).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess) {
                        Log.e("token", response.body().token);
                    }
                } else {
                    Log.e("response", "psw_false");

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("onfailure", t.getMessage());
                Toast.makeText(Register.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });
    }

}