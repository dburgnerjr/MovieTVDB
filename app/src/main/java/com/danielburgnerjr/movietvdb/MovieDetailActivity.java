package com.danielburgnerjr.movietvdb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.danielburgnerjr.movietvdb.adapter.ReviewAdapter;
import com.danielburgnerjr.movietvdb.adapter.VideoAdapter;
import com.danielburgnerjr.movietvdb.api.MovieTVAPI;
import com.danielburgnerjr.movietvdb.model.Movie;
import com.danielburgnerjr.movietvdb.model.Review;
import com.danielburgnerjr.movietvdb.model.TV;
import com.danielburgnerjr.movietvdb.model.Video;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
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

import com.danielburgnerjr.movietvdb.data.MovieTVDBContract;
import com.danielburgnerjr.movietvdb.data.MovieTVDbHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
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
    private SQLiteDatabase mDb;
    AdView mAdView;

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

    Button mFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MobileAds.initialize(this, String.valueOf(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        String strDescription = null;
        String strReleaseDate = null;
        double dUserRating = 0.0;
        String strPoster = null;
        String strBackdrop = null;

        ivBackdrop = findViewById(R.id.ivBackdrop);
        ivPoster = findViewById(R.id.movie_poster);
        tvDescription = findViewById(R.id.movie_description);
        tvReleaseDateHeading = findViewById(R.id.release_date_heading);
        tvReleaseDate = findViewById(R.id.release_date);
        rbRating = findViewById(R.id.rating);
        tvVideosHeading = findViewById(R.id.videos_heading);
        rvVideoList = findViewById(R.id.video_list);
        tvReviewsHeading = findViewById(R.id.reviews_heading);
        rvReviews = findViewById(R.id.reviews);
        mFavoriteButton = findViewById(R.id.favorite_button);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError("Intent is null");
        }

        Bundle data = getIntent().getExtras();
        if (data == null) {
            closeOnError(getString(R.string.Data_Not_Found));
            return;
        }

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = data.getParcelable(EXTRA_MOVIE);
            if (mMovie == null) {
                closeOnError(getString(R.string.Data_Not_Found));
                return;
            }
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            tTV = data.getParcelable(EXTRA_TV);
            if (tTV == null) {
                closeOnError(getString(R.string.Data_Not_Found));
                return;
            }
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie or TV parcelable");
        }

        Toolbar tbToolbar = findViewById(R.id.tbToolbar);
        setSupportActionBar(tbToolbar);
        CollapsingToolbarLayout ctlToolbarLayout = findViewById(R.id.toolbar_layout);
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
            tvReleaseDateHeading.setText(R.string.release_date_title);
            strReleaseDate = mMovie.getReleaseDate();
            dUserRating = mMovie.getUserRating();
            strPoster = mMovie.getPoster();
            strBackdrop = mMovie.getBackdrop();
            if (!mMovie.isFavorite()) {
                mFavoriteButton.setText(R.string.favorite);
            } else {
                mFavoriteButton.setText(R.string.unfavorite);
            }
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            strDescription = tTV.getDescription();
            tvReleaseDateHeading.setText(R.string.first_air_date_title);
            strReleaseDate = tTV.getReleaseDate();
            dUserRating = tTV.getUserRating();
            strPoster = tTV.getPoster();
            strBackdrop = tTV.getBackdrop();
            if (!tTV.isFavorite()) {
                mFavoriteButton.setText(R.string.favorite);
            } else {
                mFavoriteButton.setText(R.string.unfavorite);
            }

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
        mReviewAdapter = new ReviewAdapter(new ArrayList<Review>(), this);
        rvReviews.setAdapter(mReviewAdapter);

        // Fetch reviews only if savedInstanceState == null
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_REVIEWS)) {
            List<Review> reviews = savedInstanceState.getParcelableArrayList(EXTRA_REVIEWS);
            mReviewAdapter.add(reviews);
        } else {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                fetchReviews(Long.parseLong(mMovie.getId()));
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                fetchReviews(Long.parseLong(tTV.getId()));
            }
        }

        MovieTVDbHelper pmDbHelper = new MovieTVDbHelper(this);
        mDb = pmDbHelper.getWritableDatabase();

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getIntent().hasExtra(EXTRA_MOVIE)) {
                    if (mMovie.isFavorite()) {
                        mMovie.setFavorite(false);
                        mFavoriteButton.setText(R.string.favorite);
                        if (removeFromFavorites() != 0)
                            Toast.makeText(MovieDetailActivity.this, "This movie was successfully removed from your favorites.", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MovieDetailActivity.this, "This movie was unsuccessfully removed from your favorites.", Toast.LENGTH_LONG).show();
                    } else {
                        mMovie.setFavorite(true);
                        mFavoriteButton.setText(R.string.unfavorite);
                        if (addToFavorites() != 0)
                            Toast.makeText(MovieDetailActivity.this, "This movie was successfully added to your favorites.", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MovieDetailActivity.this, "This movie was unsuccessfully added to your favorites.", Toast.LENGTH_LONG).show();
                    }
                } else if (getIntent().hasExtra(EXTRA_TV)) {
                    if (tTV.isFavorite()) {
                        tTV.setFavorite(false);
                        mFavoriteButton.setText(R.string.favorite);
                        if (removeFromFavorites() != 0)
                            Toast.makeText(MovieDetailActivity.this, "This TV show was successfully removed from your favorites.", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MovieDetailActivity.this, "This TV show was unsuccessfully removed from your favorites.", Toast.LENGTH_LONG).show();
                        removeFromFavorites();
                    } else {
                        tTV.setFavorite(true);
                        mFavoriteButton.setText(R.string.unfavorite);
                        if (addToFavorites() != 0)
                            Toast.makeText(MovieDetailActivity.this, "This TV show was successfully added to your favorites.", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MovieDetailActivity.this, "This TV show was unsuccessfully added to your favorites.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Picasso.get()
                .load(TMDB_IMAGE_PATH + strPoster)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(ivPoster);
        Picasso.get()
                .load(TMDB_IMAGE_PATH + strBackdrop)
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

    private long addToFavorites() {
        ContentValues cv = new ContentValues();
        long rowCount = 0L;
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_ID, mMovie.getId());
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_ORIGINALTITLE, mMovie.getTitle());
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_OVERVIEW, mMovie.getDescription());
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_POSTERPATH, mMovie.getPoster());
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_BACKDROP, mMovie.getBackdrop());
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_RELEASEDATE, mMovie.getReleaseDate());
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE, mMovie.getUserRating());

            rowCount = mDb.insert(MovieTVDBContract.MovieEntry.TABLE_NAME, null, cv);
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_ID, tTV.getId());
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_ORIGINALTITLE, tTV.getTitle());
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_OVERVIEW, tTV.getDescription());
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_POSTERPATH, tTV.getPoster());
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_BACKDROP, tTV.getBackdrop());
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_RELEASEDATE, tTV.getReleaseDate());
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_VOTEAVERAGE, tTV.getUserRating());

            rowCount = mDb.insert(MovieTVDBContract.TVEntry.TABLE_NAME, null, cv);
        }
        return rowCount;
    }

    private int removeFromFavorites() {
        int rowCount = 0;
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            Uri uri = MovieTVDBContract.MovieEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(mMovie.getId()).build();
            rowCount = getContentResolver().delete(uri, null, null);
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            Uri uri = MovieTVDBContract.TVEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(tTV.getId()).build();
            rowCount = getContentResolver().delete(uri, null, null);
        }
        return rowCount;
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
