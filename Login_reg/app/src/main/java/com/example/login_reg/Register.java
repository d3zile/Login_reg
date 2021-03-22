    package com.example.login_reg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class Register extends AppCompatActivity {


        Button btnSignUp;
        EditText PassSignUp, EmailSignUp, FName,LName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSignUp = findViewById(R.id.SignUp);
        PassSignUp = findViewById(R.id.edPass);
        EmailSignUp = findViewById(R.id.edEmail);
        FName = findViewById(R.id.edFName);
        LName = findViewById(R.id.edLName);

    }
        public void onClick(View view){
            Intent i = new Intent(Register.this, MainActivity.class);
            startActivity(i);
        }
        public void onClickReg(View view){
            if (PassSignUp.getText().toString().isEmpty()) {

                Toast toast = Toast.makeText(getApplicationContext(),"Поля логина и пароля не могут быть пустыми",Toast.LENGTH_SHORT);
                toast.show();
            }
            if (EmailSignUp.getText().toString().isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(),"Поля логина и пароля не могут быть пустыми",Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setEmail(EmailSignUp.getText().toString());
                registerRequest.setFirstName(FName.getText().toString());
                registerRequest.setLastName(LName.getText().toString());
                registerRequest.setPassword(PassSignUp.getText().toString());
                SignUpUser(registerRequest);
            }
        }
        public void SignUpUser(RegisterRequest registerRequest){
            retrofit2.Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
            registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(retrofit2.Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(Register.this, "Successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Register.this, MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(Register.this, "An error occurred please try again later...", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Toast.makeText(Register.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }