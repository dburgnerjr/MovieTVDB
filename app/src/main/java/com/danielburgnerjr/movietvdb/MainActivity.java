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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvRecyclerView;
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRecyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        rvRecyclerView.setHasFixedSize(true);
        rvRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        rvRecyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        mAdapter = new MovieAdapter(this);
        rvRecyclerView.setAdapter(mAdapter);
        //getPopularMovies();

        Spinner spnMenuOptions = (Spinner) findViewById(R.id.spnMenuOptions);

        String[] strOptions = new String[] { "Top Rated Movies", "Upcoming Movies", "Now Playing",
                "Popular Movies", "Popular TV Shows", "Top Rated TV Shows"};

        ArrayAdapter<String> arAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, strOptions);

        spnMenuOptions.setAdapter(arAdapter);

        spnMenuOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch ((String)parent.getItemAtPosition(position)) {
                    case "Top Rated Movies":
                        getTopRatedMovies();
                        break;
                    case "Upcoming Movies":
                        getUpcomingMovies();
                        break;
                    case "Now Playing":
                        getNowPlayingMovies();
                        break;
                    case "Popular Movies":
                        getPopularMovies();
                        break;
                    case "Popular TV Shows":
                        getPopularTVShows();
                        break;
                    case "Top Rated TV Shows":
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
                mAdapter.setMovieList(movieResult.getResults());
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
                mAdapter.setMovieList(movieResult.getResults());
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
                mAdapter.setMovieList(movieResult.getResults());
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
                mAdapter.setMovieList(movieResult.getResults());
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
        mtaService.getPopularTVShows(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
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
        mtaService.getTopRatedTVShows(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImageView;
        public MovieViewHolder(View vItemView) {
            super(vItemView);
            ivImageView = (ImageView) vItemView.findViewById(R.id.ivImageView);
        }
    }

    public static class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
        private List<Movie> mMovieList;
        private LayoutInflater liInflater;
        private Context conContext;

        public MovieAdapter(Context conC) {
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
                    intI.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(nPos));
                    conContext.startActivity(intI);
                }
            });
            return mvhHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder mvhH, int nP) {
            Movie mM = mMovieList.get(nP);
            Picasso.with(conContext)
                    .load(mM.getPoster())
                    .placeholder(R.color.colorAccent)
                    .into(mvhH.ivImageView);
        }

        @Override
        public int getItemCount() {
            return (mMovieList == null) ? 0 : mMovieList.size();
        }

        public void setMovieList(List<Movie> ml) {
            this.mMovieList = new ArrayList<Movie>();
            this.mMovieList.addAll(ml);
            notifyDataSetChanged();
        }
    }
}
