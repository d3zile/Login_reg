package com.example.autinfication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.autinfication.Service.MovieApi;
import com.example.autinfication.Service.ScrollApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    RecyclerView mRecyclerView, mRecyclerView1;

    List<Movie> mMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mMovie = new ArrayList<>();


        mRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1) ;
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        mRecyclerView1.setLayoutManager(layoutManager1);
        mRecyclerView.setLayoutManager(layoutManager);




        MovieAdapter adapter = new MovieAdapter(mMovie);
        mRecyclerView.setAdapter(adapter);

        ScrollAdapter adapter1 = new ScrollAdapter(mMovie);
        mRecyclerView1.setAdapter(adapter1);

        mProgressBar.setVisibility(View.VISIBLE);

        MovieApi movieApi = MovieApi.retrofit.create(MovieApi.class);
        final Call<List<Movie>> call = movieApi.getData();
        call.enqueue(new Callback<List<Movie>>() {
                         @Override
                         public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                             // response.isSuccessfull() возвращает true если код ответа 2xx
                             if (response.isSuccessful()) {
                                 mMovie.addAll(response.body());
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
                         public void onFailure(Call<List<Movie>> call, Throwable throwable) {
                             Toast.makeText(MainActivity.this, "Что-то пошло не так",
                                     Toast.LENGTH_SHORT).show();
                             mProgressBar.setVisibility(View.INVISIBLE);
                         }
                     }
        );
        ScrollApi scrollApi = ScrollApi.retrofit.create(ScrollApi.class);
        final Call<List<Movie>> call1 = scrollApi.getData();
        call1.enqueue(new Callback<List<Movie>>() {
                          @Override
                          public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                              // response.isSuccessfull() возвращает true если код ответа 2xx
                              if (response.isSuccessful()) {
                                  mMovie.addAll(response.body());
                                  mRecyclerView1.getAdapter().notifyDataSetChanged();
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
                          public void onFailure(Call<List<Movie>> call, Throwable throwable) {
                              Toast.makeText(MainActivity.this, "Что-то пошло не так",
                                      Toast.LENGTH_SHORT).show();
                              mProgressBar.setVisibility(View.INVISIBLE);
                          }
                      }
        );
    }
}