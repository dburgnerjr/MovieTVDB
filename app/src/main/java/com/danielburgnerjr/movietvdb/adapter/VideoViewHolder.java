package com.danielburgnerjr.movietvdb.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.danielburgnerjr.movietvdb.R;
import com.danielburgnerjr.movietvdb.model.Video;

import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class VideoViewHolder extends RecyclerView.ViewHolder {
    public final View vView;
    public ImageView ivThumbnailView;
    public Video viVideo;

    public VideoViewHolder(View vItemView) {
        super(vItemView);
        ivThumbnailView = vItemView.findViewById(R.id.trailer_thumbnail);
        ButterKnife.bind(this, vItemView);
        vView = vItemView;
    }
}
