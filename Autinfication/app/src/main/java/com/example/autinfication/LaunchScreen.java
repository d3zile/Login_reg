package com.example.autinfication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autinfication.Activity.LoginActivity;

public class LaunchScreen extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LaunchScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 5*600);
    }
}
