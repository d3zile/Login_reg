package com.example.color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    RecyclerView mRecyclerView;

    List<Flower> mFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mFlowers = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.list_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        FlowerAdapter adapter = new FlowerAdapter(mFlowers);
        mRecyclerView.setAdapter(adapter);

        mProgressBar.setVisibility(View.VISIBLE);

        FlowersAPI flowersAPI = FlowersAPI.retrofit.create(FlowersAPI.class);
        final Call<List<Flower>> call = flowersAPI.getData();
        call.enqueue(new Callback<List<Flower>>() {
                         @Override
                         public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                             // response.isSuccessfull() возвращает true если код ответа 2xx
                             if (response.isSuccessful()) {
                                 mFlowers.addAll(response.body());
                                 mRecyclerView.getAdapter().notifyDataSetChanged();
                                 mProgressBar.setVisibility(View.INVISIBLE);
                             } else {
                                 // Обрабатываем ошибку
                                 ResponseBody errorBody = response.errorBody();
                                 try {
                                     Toast.makeText(MainActivity.this, errorBody.string(),
                                             Toast.LENGTH_SHORT).show();
                                     mProgressBar.setVisibility(View.INVISIBLE);
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Flower>> call, Throwable throwable) {
                             Toast.makeText(MainActivity.this, "Что-то пошло не так",
                                     Toast.LENGTH_SHORT).show();
                             mProgressBar.setVisibility(View.INVISIBLE);
                         }
                     }
        );
    }
}