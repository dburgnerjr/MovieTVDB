package com.danielburgnerjr.movietvdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.rvRecyclerView)
    RecyclerView rvRecyclerView;
    @InjectView(R.id.spnMenuOptions)
    Spinner spnMenuOptions;
    private MovieAdapter mMovieAdapter;
    private TVAdapter mTVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        rvRecyclerView.setHasFixedSize(true);
        rvRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        rvRecyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        mMovieAdapter = new MovieAdapter(this);
        mTVAdapter = new TVAdapter(this);
        rvRecyclerView.setAdapter(mMovieAdapter);
        getPopularMovies();

        String[] strOptions = new String[] { "Popular Movies", "Now Playing", "Top Rated Movies",
                "Upcoming Movies", "Popular TV Shows", "Top Rated TV Shows"};

        ArrayAdapter<String> arAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, strOptions);

        spnMenuOptions.setAdapter(arAdapter);

        spnMenuOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch ((String)parent.getItemAtPosition(position)) {
                    case "Top Rated Movies":
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getTopRatedMovies();
                        break;
                    case "Upcoming Movies":
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getUpcomingMovies();
                        break;
                    case "Now Playing":
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getNowPlayingMovies();
                        break;
                    case "Popular Movies":
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getPopularMovies();
                        break;
                    case "Popular TV Shows":
                        rvRecyclerView.setAdapter(mTVAdapter);
                        getPopularTVShows();
                        break;
                    case "Top Rated TV Shows":
                        rvRecyclerView.setAdapter(mTVAdapter);
                        getTopRatedTVShows();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void getPopularMovies() {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade rfRequest) {
                        rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        mtaService.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mMovieAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getTopRatedMovies() {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade rfRequest) {
                        rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        mtaService.getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mMovieAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getNowPlayingMovies() {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade rfRequest) {
                        rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        mtaService.getNowPlayingMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mMovieAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getUpcomingMovies() {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade rfRequest) {
                        rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        mtaService.getUpcomingMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mMovieAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getPopularTVShows() {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade rfRequest) {
                        rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        mtaService.getPopularTVShows(new Callback<TV.TVResult>() {
            @Override
            public void success(TV.TVResult tvResult, Response response) {
                mTVAdapter.setTVList(tvResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getTopRatedTVShows() {
        RestAdapter raAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade rfRequest) {
                        rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTVAPI mtaService = raAdapter.create(MovieTVAPI.class);
        mtaService.getTopRatedTVShows(new Callback<TV.TVResult>() {
            @Override
            public void success(TV.TVResult tvResult, Response response) {
                mTVAdapter.setTVList(tvResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
