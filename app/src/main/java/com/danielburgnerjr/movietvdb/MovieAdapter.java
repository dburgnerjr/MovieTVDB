package com.danielburgnerjr.movietvdb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private List<Movie> mMovieList;
    private LayoutInflater liInflater;
    private Context conContext;

    public MovieAdapter(Context conC) {
        this.conContext = conC;
        this.liInflater = LayoutInflater.from(conC);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup vgParent, final int nViewType) {
        View vView = liInflater.inflate(R.layout.movie_list, vgParent, false);
        final MovieViewHolder mvhHolder = new MovieViewHolder(vView);
        vView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vV) {
                int nPos = mvhHolder.getAdapterPosition();
                Intent intI = new Intent(conContext, MovieDetailActivity.class);
                intI.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(nPos));
                conContext.startActivity(intI);
            }
        });
        return mvhHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder mvhH, int nP) {
        Movie mM = mMovieList.get(nP);
        Picasso.get()
                .load(mM.getPoster())
                .placeholder(R.color.colorAccent)
                .into(mvhH.ivImageView);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(List<Movie> ml) {
        this.mMovieList = new ArrayList<Movie>();
        this.mMovieList.addAll(ml);
        notifyDataSetChanged();
    }
}
