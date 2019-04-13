package com.danielburgnerjr.movietvdb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

import java.util.List;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.squareup.picasso.Picasso;
/**
 * Created by dburgnerjr on 6/5/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements VideoAdapter.Callbacks, ReviewAdapter.Callbacks {
    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_TV = "tv";
    public static final String EXTRA_VIDEOS = "video";
    public static final String EXTRA_REVIEWS = "review";
    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

    private Movie mMovie;
    private TV tTV;
    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;

    ImageView ivBackdrop;
    ImageView ivPoster;
    TextView tvDescription;
    TextView tvReleaseDateHeading;
    TextView tvReleaseDate;
    RatingBar rbRating;
    TextView tvVideosHeading;
    RecyclerView rvVideoList;
    TextView tvReviewsHeading;
    RecyclerView rvReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String strDescription = null;
        String strReleaseDate = null;
        double dUserRating = 0.0;
        String strPoster = null;
        String strBackdrop = null;

        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);
        ivPoster = (ImageView) findViewById(R.id.movie_poster);
        tvDescription = (TextView) findViewById(R.id.movie_description);
        tvReleaseDateHeading = (TextView) findViewById(R.id.release_date_heading);
        tvReleaseDate = (TextView) findViewById(R.id.release_date);
        rbRating = (RatingBar) findViewById(R.id.rating);
        tvVideosHeading = (TextView) findViewById(R.id.videos_heading);
        rvVideoList = (RecyclerView) findViewById(R.id.video_list);
        tvReviewsHeading = (TextView) findViewById(R.id.reviews_heading);
        rvReviews = (RecyclerView) findViewById(R.id.reviews);

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

        LinearLayout llMovieLayout = new LinearLayout(getApplicationContext());
        llMovieLayout.setOrientation(LinearLayout.HORIZONTAL);

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

        // For horizontal list of trailers
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvVideoList.setLayoutManager(layoutManager);
        mVideoAdapter = new VideoAdapter(new ArrayList<Video>(),  this);
        rvVideoList.setAdapter(mVideoAdapter);
        rvVideoList.setNestedScrollingEnabled(false);

        // Fetch trailers only if savedInstanceState == null
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_VIDEOS)) {
            List<Video> videos = savedInstanceState.getParcelableArrayList(EXTRA_VIDEOS);
            mVideoAdapter.addVideo(videos);
        } else {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                fetchTrailers(Long.parseLong(mMovie.getId()));
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                fetchTrailers(Long.parseLong(tTV.getId()));
            }
        }

        // For vertical list of reviews
        LinearLayoutManager llmReviews
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvReviews.setLayoutManager(llmReviews);
        mReviewAdapter = new ReviewAdapter(new ArrayList<Review>(), (ReviewAdapter.Callbacks) this);
        rvReviews.setAdapter(mReviewAdapter);

        // Fetch reviews only if savedInstanceState == null
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_REVIEWS)) {
            List<Review> reviews = savedInstanceState.getParcelableArrayList(EXTRA_REVIEWS);
            mReviewAdapter.add(reviews);
        } else {
            fetchReviews(Long.parseLong(mMovie.getId()));
        }

        Picasso.get()
                .load(strPoster)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(ivPoster);
        Picasso.get()
                .load(strBackdrop)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(ivBackdrop);

    }

    private void fetchTrailers(long lMovieId) {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mtaService.getMovieVideos(lMovieId, new Callback<Video.VideoResult>() {
                @Override
                public void success(Video.VideoResult videoResult, Response response) {
                    mVideoAdapter.setVideoList(videoResult.getVideoList());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            mtaService.getTVVideos(lMovieId, new Callback<Video.VideoResult>() {
                @Override
                public void success(Video.VideoResult videoResult, Response response) {
                    mVideoAdapter.setVideoList(videoResult.getVideoList());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }
    }

    private void fetchReviews(long lMovieId) {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mtaService.getMovieReviews(lMovieId, new Callback<Review.ReviewResult>() {
                @Override
                public void success(Review.ReviewResult reviewResult, Response response) {
                    mReviewAdapter.setReviews(reviewResult.getReviews());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            mtaService.getTVReviews(lMovieId, new Callback<Review.ReviewResult>() {
                @Override
                public void success(Review.ReviewResult reviewResult, Response response) {
                    mReviewAdapter.setReviews(reviewResult.getReviews());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }
    }

    private void closeOnError(String msg) {
        finish();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void watch(Video video, int nPosition) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" +
                video.getKey())));
    }

    @Override
    public void read(Review review, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl())));
    }
}
