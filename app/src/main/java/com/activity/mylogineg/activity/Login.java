package com.activity.mylogineg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.activity.mylogineg.R;
import com.activity.mylogineg.api.ApiInterface;
import com.activity.mylogineg.response.LoginResponse;
import com.activity.mylogineg.service.Token;
import com.activity.mylogineg.simple.RetrofitServiceLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText edtPhoneNumberLogin, edtPasswordLogin;
    private Button btnLogin, btnToRegister, btnToDoctor;
    private RetrofitServiceLogin service;
    private String token;
    private ProgressBar progressBar;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
//        Log.e("login:token", "success");
    }

    private void init() {
        service = new RetrofitServiceLogin();

        edtPhoneNumberLogin = findViewById(R.id.edtPhoneNumberLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnToRegister = findViewById(R.id.btnToRegister);
        btnLogin = findViewById(R.id.btnLogin);

      //  findViewById(R.id.btnLogin).setOnClickListener(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(Long.parseLong(edtPhoneNumberLogin.getText().toString()),edtPasswordLogin.getText().toString());

            }
        });
        //findViewById(R.id.btnToRegister).setOnClickListener(this);
        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("login:token", "success");
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }


//      @Override
//      public void onClick(View v) {
//         switch (v.getId()) {
//             case R.id.btnLogin:
//                  login(Long.parseLong(edtPhoneNumberLogin.getText().toString()), edtPasswordLogin.getText().toString());
//                  Log.e("login:token", "success");
//                  break;
//               case R.id.btnToRegister:
//                  startActivity(new Intent(getApplicationContext(), Register.class));
//                 break;
//         }
//      }


    private void login(long phone, String pass) {

        ApiInterface apiInterface;
        apiInterface = service.getService().create(ApiInterface.class);//RetrofitServiceLogin.Retrofit.interfaceOfApiInterface

        apiInterface.login(phone, pass).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().is_success) {
                        Log.e("token", response.body().token);//to token give out on logcat

                        token=response.body().token;
                        
                        Token.MyToken.setToken(token);
//
//                        editor.putString("token",token);
//                        editor.apply();
//                        editor.commit();

                        Intent intent=new Intent(getApplicationContext(), DoctorListActivity.class);
                        intent.putExtra("Token",response.body().token);//putting extra
//                        Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);
                        finish();
                } else {
                        Toast.makeText(Login.this,"Login failed",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//
//                //Toast.makeText(Login.this,t.getMessage(),Toast.LENGTH_LONG).show();
//            }
        });
    }
}
