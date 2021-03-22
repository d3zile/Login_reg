package com.example.autinfication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autinfication.Activity.LoginActivity;
import com.example.autinfication.Request.RegisterRequest;
import com.example.autinfication.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
TextView tvSign;
EditText edEmail, edName, edPass, edPassCon, edLastName;
Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvSign = findViewById(R.id.tvSign);
        edEmail = findViewById(R.id.edEmail);
        edName = findViewById(R.id.edName);
        edPass = findViewById(R.id.edPass);
        edPassCon = findViewById(R.id.edPassCon);
        btnRegister = findViewById(R.id.btn_register);
        edLastName = findViewById(R.id.edSecondName);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(TextUtils.isEmpty(edEmail.getText().toString())||TextUtils.isEmpty(edPass.getText().toString())||TextUtils.isEmpty(edName.getText().toString())||TextUtils.isEmpty(edLastName.getText().toString())){
                        String message = "Заполните все поля";
                        Toast.makeText(RegisterActivity.this, message,Toast.LENGTH_LONG).show();
                    }
                   else {

                        registerUser();
                    }

            }
        });


        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void registerUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(edEmail.getText().toString());
        registerRequest.setPassword(edPass.getText().toString());
        registerRequest.setFirstName(edName.getText().toString());
        registerRequest.setLastName(edLastName.getText().toString());
        Call<RegisterResponse> registerResponseCall = ApiClient.getRegister().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
            if (response.isSuccessful()){
                String message = "Все ок...";
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

                finish();
            }else {

                String message = "Что-то пошло не так";
                Toast.makeText(RegisterActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = "Регистрация прошла умпешно";
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });
    }
}