package com.danielburgnerjr.movietvdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    public final View vView;
    public ImageView ivThumbnailView;
    public Video viVideo;

    public VideoViewHolder(View vItemView) {
        super(vItemView);
        ivThumbnailView = (ImageView) vItemView.findViewById(R.id.trailer_thumbnail);
        ButterKnife.bind(this, vItemView);
        vView = vItemView;
    }
}