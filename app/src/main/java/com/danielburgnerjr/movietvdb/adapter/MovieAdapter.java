package com.danielburgnerjr.movietvdb.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielburgnerjr.movietvdb.MovieDetailActivity;
import com.danielburgnerjr.movietvdb.R;
import com.danielburgnerjr.movietvdb.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private List<Movie> mMovieList;
    private LayoutInflater liInflater;
    private Context conContext;
    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

    public MovieAdapter(Context conC) {
        this.conContext = conC;
        this.liInflater = LayoutInflater.from(conC);
    }

    @Override
    @NonNull
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup vgParent, final int nViewType) {
        View vView = liInflater.inflate(R.layout.movie_list, vgParent, false);
        final MovieViewHolder mvhHolder = new MovieViewHolder(vView);
        vView.setOnClickListener(vV -> {
            int nPos = mvhHolder.getAdapterPosition();
            Intent intI = new Intent(conContext, MovieDetailActivity.class);
            intI.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(nPos));
            conContext.startActivity(intI);
        });
        return mvhHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder mvhH, int nP) {
        Movie mM = mMovieList.get(nP);
        Picasso.get()
                .load(TMDB_IMAGE_PATH + mM.getPoster())
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(mvhH.ivImageView);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(List<Movie> ml) {
        this.mMovieList = new ArrayList<>();
        this.mMovieList.addAll(ml);
        notifyDataSetChanged();
    }
}
