package com.danielburgnerjr.movietvdb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.ButterKnife;

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
        String strDescription = null;
        String strReleaseDate = null;
        double dUserRating = 0.0;
        String strPoster = null;
        String strBackdrop = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);
        ivPoster = (ImageView) findViewById(R.id.movie_poster);
        tvDescription = (TextView) findViewById(R.id.movie_description);
        tvReleaseDateHeading = (TextView) findViewById(R.id.release_date_heading);
        tvReleaseDate = (TextView) findViewById(R.id.release_date);
        rbRating = (RatingBar) findViewById(R.id.rating);

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

        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            strDescription = mMovie.getDescription();
            tvReleaseDateHeading.setText("Release Date");
            strReleaseDate = mMovie.getReleaseDate();
            dUserRating = mMovie.getUserRating();
            strPoster = mMovie.getPoster();
            strBackdrop = mMovie.getBackdrop();
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            strDescription = tTV.getDescription();
            tvReleaseDateHeading.setText("First Air Date");
            strReleaseDate = tTV.getReleaseDate();
            dUserRating = tTV.getUserRating();
            strPoster = tTV.getPoster();
            strBackdrop = tTV.getBackdrop();
        }

        tvDescription.setText(strDescription);
        tvReleaseDate.setText(strReleaseDate);
        rbRating.setRating((float)dUserRating);

        Picasso.get()
                .load(strPoster)
                .into(ivPoster);
        Picasso.get()
                .load(strBackdrop)
                .into(ivBackdrop);

    }
}
