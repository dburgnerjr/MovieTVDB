package com.danielburgnerjr.movietvdb;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private static final String CURRENT_RECYCLER_VIEW_POSITION = "CurrentRecyclerViewPosition";
    RecyclerView rvRecyclerView;
    Spinner spnMenuOptions;
    private MovieAdapter mMovieAdapter;
    private TVAdapter mTVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvRecyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        spnMenuOptions = (Spinner) findViewById(R.id.spnMenuOptions);

        ButterKnife.bind(this);
        rvRecyclerView.setHasFixedSize(true);
        rvRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        rvRecyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        mMovieAdapter = new MovieAdapter(this);
        mTVAdapter = new TVAdapter(this);
        rvRecyclerView.setAdapter(mMovieAdapter);
        getPopularMovies();

        String[] strOptions = getResources().getStringArray(R.array.sort_options);

        ArrayAdapter<String> arAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, strOptions);

        spnMenuOptions.setAdapter(arAdapter);

        spnMenuOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getPopularMovies();
                        break;
                    case 1:
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getTopRatedMovies();
                        break;
                    case 2:
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getNowPlayingMovies();
                        break;
                    case 3:
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getUpcomingMovies();
                        break;
                    case 4:
                        rvRecyclerView.setAdapter(mMovieAdapter);
                        getFavoriteMovies();
                        break;
                    case 5:
                        rvRecyclerView.setAdapter(mTVAdapter);
                        getPopularTVShows();
                        break;
                    case 6:
                        rvRecyclerView.setAdapter(mTVAdapter);
                        getTopRatedTVShows();
                        break;
                    case 7:
                        rvRecyclerView.setAdapter(mTVAdapter);
                        getFavoriteTVShows();
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

    private void getFavoriteMovies() {
        Cursor cursor = mDb.query(PopularMoviesContract.PopularMoviesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_VOTEAVERAGE);

        //TODO Build the movie list from the stored Ids
        List<Movie> result = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_ID));

                Movie movC = new Movie(
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_ORIGINALTITLE)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_POSTERPATH)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_BACKDROP)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_RELEASEDATE)),
                        cursor.getDouble(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_VOTEAVERAGE)),
                        true);
                System.out.println(movC.getPoster() + " " + movC.getBackdrop());
                result.add(movC);
            }
        } finally {
            cursor.close();
        }
        maAdapter.setMovieList(result);
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

    private void getFavoriteTVShows() {
        Cursor cursor = mDb.query(PopularMoviesContract.PopularMoviesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_VOTEAVERAGE);

        //TODO Build the movie list from the stored Ids
        List<Movie> result = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_ID));

                Movie movC = new Movie(
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_ORIGINALTITLE)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_POSTERPATH)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_BACKDROP)),
                        cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_RELEASEDATE)),
                        cursor.getDouble(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_NAME_VOTEAVERAGE)),
                        true);
                System.out.println(movC.getPoster() + " " + movC.getBackdrop());
                result.add(movC);
            }
        } finally {
            cursor.close();
        }
        maAdapter.setMovieList(result);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GridLayoutManager layoutManager = (GridLayoutManager) rvRecyclerView.getLayoutManager();
        outState.putInt(CURRENT_RECYCLER_VIEW_POSITION, layoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            int currentPosition = savedInstanceState.getInt(CURRENT_RECYCLER_VIEW_POSITION);
            rvRecyclerView.scrollToPosition(currentPosition);
        }
    }
}
