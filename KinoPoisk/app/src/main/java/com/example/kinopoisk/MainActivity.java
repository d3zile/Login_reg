package com.example.kinopoisk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    List<Movies> mMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovies = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        MoviesAdapter adapter = new MoviesAdapter(mMovies);
        mRecyclerView.setAdapter(adapter);

        ApiServise apiServise = ApiServise.retrofit.create(ApiServise.class);

        final Call<List<Movies>> call = apiServise.getData();
        call.enqueue(new Callback<List<Movies>>() {
            @Override
            public void onResponse(Call<List<Movies>> call, Response<List<Movies>> response) {
                mMovies.addAll(response.body());
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "dd",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Movies>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "chlen",Toast.LENGTH_LONG).show();

            }
        });
    }
}
