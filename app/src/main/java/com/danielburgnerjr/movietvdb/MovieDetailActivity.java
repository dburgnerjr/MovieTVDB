package com.danielburgnerjr.movietvdb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
/**
 * Created by dburgnerjr on 6/5/17.
 */

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_TV = "tv";

    private Movie mMovie;
    private TV tTV;
    ImageView ivBackdrop;
    ImageView ivPoster;
    TextView tvDescription;
    TextView tvReleaseDateHeading;
    TextView tvReleaseDate;
    RatingBar rbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            tTV = getIntent().getParcelableExtra(EXTRA_TV);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie or TV parcelable");
        }

        Toolbar tbToolbar = (Toolbar) findViewById(R.id.tbToolbar);
        setSupportActionBar(tbToolbar);
        CollapsingToolbarLayout ctlToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            ctlToolbarLayout.setTitle(mMovie.getTitle());
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            ctlToolbarLayout.setTitle(tTV.getTitle());
        }
        ctlToolbarLayout.setExpandedTitleColor(Color.WHITE);
        ctlToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);
        tvDescription = (TextView) findViewById(R.id.movie_description);
        ivPoster = (ImageView) findViewById(R.id.movie_poster);
        tvReleaseDateHeading = (TextView) findViewById(R.id.release_date_heading);
        tvReleaseDate = (TextView) findViewById(R.id.release_date);
        rbRating = (RatingBar) findViewById(R.id.rating);


        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            tvDescription.setText(mMovie.getDescription());
            tvReleaseDateHeading.setText("Release Date");
            tvReleaseDate.setText(mMovie.getReleaseDate());
            rbRating.setRating((float)mMovie.getUserRating());

            Picasso.with(this)
                    .load(mMovie.getPoster())
                    .into(ivPoster);
            Picasso.with(this)
                    .load(mMovie.getBackdrop())
                    .into(ivBackdrop);
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            tvDescription.setText(tTV.getDescription());
            tvReleaseDateHeading.setText("First Air Date");
            tvReleaseDate.setText(tTV.getReleaseDate());
            rbRating.setRating((float)tTV.getUserRating());

            Picasso.with(this)
                    .load(tTV.getPoster())
                    .into(ivPoster);
            Picasso.with(this)
                    .load(tTV.getBackdrop())
                    .into(ivBackdrop);
        }
    }
}
