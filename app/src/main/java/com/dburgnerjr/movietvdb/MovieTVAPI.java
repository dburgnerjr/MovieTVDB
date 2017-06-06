package com.dburgnerjr.movietvdb;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dburgnerjr on 6/5/17.
 */
public interface MovieTVAPI {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);
}
