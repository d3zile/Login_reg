package com.example.login_reg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnlogin;
    EditText email,pass;
    TextView noAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = findViewById(R.id.SignIn);
        email = findViewById(R.id.LogEmail);
        pass = findViewById(R.id.LogPass);
        noAcc = findViewById(R.id.CreateNewAcc);
        noAcc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(pass.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Inputs required", Toast.LENGTH_LONG).show();
                }
                else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(email.getText().toString());
                    loginRequest.setPassword(pass.getText().toString());

                    SignInUser(loginRequest);
                }
            }
        });

    }
    public void SignInUser(LoginRequest loginRequest){
        retrofit2.Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "An error occurred please try again later...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}