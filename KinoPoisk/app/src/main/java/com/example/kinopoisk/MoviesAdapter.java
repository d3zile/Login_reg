package com.example.kinopoisk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{
    private final static String PHOTO_URL = "http://cinema.areas.su/up/images/";
    private List<Movies> mMovies;
    private Context mContext;

    MoviesAdapter(List<Movies> flowers) {
        this.mMovies = flowers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movies movie = mMovies.get(position);
        holder.nameTextView.setText(movie.getName());
        holder.description.setText(movie.getDescription());

        Picasso.with(mContext)
                .load(PHOTO_URL + movie.getPoster())
                .resize(300, 500)
                .into(holder.flowerImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, description;
        ImageView flowerImageView;

        ViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.textView2);
            nameTextView = (TextView) itemView.findViewById(R.id.textView);
            flowerImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}