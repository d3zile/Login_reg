package com.example.jsontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView mDateTextView;
    TextView mTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDateTextView = (TextView) findViewById(R.id.textview_date);
        mTimeTextView = (TextView) findViewById(R.id.textview_time);
        fetchDateTime();

    }

    void fetchDateTime() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://date.jsontest.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsontestAPI api = retrofit.create(JsontestAPI.class);
        Call<ServerTime> serverTimeCall = api.getServerDateTime();
        serverTimeCall.enqueue(new Callback<ServerTime>() {
            @Override
            public void onResponse(Call<ServerTime> call, Response<ServerTime> response) {
                ServerTime serverTime = response.body();
                mDateTextView.setText("Дата: " + serverTime.getDate());
                mTimeTextView.setText("Время: " + serverTime.getTime());
            }

            @Override
            public void onFailure(Call<ServerTime> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}