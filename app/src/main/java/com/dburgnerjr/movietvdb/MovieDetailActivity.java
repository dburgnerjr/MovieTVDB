package com.dburgnerjr.movietvdb;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
/**
 * Created by dburgnerjr on 6/5/17.
 */

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";

    private Movie mMovie;
    ImageView ivBackdrop;
    ImageView ivPoster;
    TextView tvTitle;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        Toolbar tbToolbar = (Toolbar) findViewById(R.id.tbToolbar);
        setSupportActionBar(tbToolbar);
        CollapsingToolbarLayout ctlToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        ctlToolbarLayout.setTitle(mMovie.getTitle());

        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);
        tvTitle = (TextView) findViewById(R.id.movie_title);
        tvDescription = (TextView) findViewById(R.id.movie_description);
        ivPoster = (ImageView) findViewById(R.id.movie_poster);

        tvTitle.setText(mMovie.getTitle());
        tvDescription.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(ivPoster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(ivBackdrop);

    }
}
