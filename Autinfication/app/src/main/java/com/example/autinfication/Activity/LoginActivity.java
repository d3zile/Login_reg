package com.example.autinfication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autinfication.ApiClient;
import com.example.autinfication.MainActivity;
import com.example.autinfication.R;
import com.example.autinfication.RegisterActivity;
import com.example.autinfication.Request.LoginRequest;
import com.example.autinfication.Response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edEmail, edPass;
    TextView tvReg;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPass);
        tvReg = findViewById(R.id.tvReg);
        btnLogin = findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString())||TextUtils.isEmpty(edPass.getText().toString())){
                    String message = "Заполните все поля";
                    Toast.makeText(LoginActivity.this, message,Toast.LENGTH_LONG).show();
                }else {
                    loginUser();
                }
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void  loginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edEmail.getText().toString());
        loginRequest.setPassword(edPass.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){

                  String message = "Все ок";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();
                }else {
                    String message = "Что-то пошло не так";
                    Toast.makeText(LoginActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}