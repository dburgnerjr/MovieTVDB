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

public class TVAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private List<TV> mTVList;
    private LayoutInflater liInflater;
    private Context conContext;

    public TVAdapter(Context conC) {
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
                intI.putExtra(MovieDetailActivity.EXTRA_TV, mTVList.get(nPos));
                conContext.startActivity(intI);
            }
        });
        return mvhHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder mvhH, int nP) {
        TV tT = mTVList.get(nP);
        Picasso.with(conContext)
                .load(tT.getPoster())
                .placeholder(R.color.colorAccent)
                .into(mvhH.ivImageView);
    }

    @Override
    public int getItemCount() {
        return (mTVList == null) ? 0 : mTVList.size();
    }

    public void setTVList(List<TV> tl) {
        this.mTVList = new ArrayList<TV>();
        this.mTVList.addAll(tl);
        notifyDataSetChanged();
    }
}
