package com.dburgnerjr.movietvdb;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dburgnerjr on 6/5/17.
 */
public interface MovieTVAPI {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/now_playing")
    void getNowPlayingMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/upcoming")
    void getUpcomingMovies(Callback<Movie.MovieResult> cb);

    @GET("/tv/popular")
    void getPopularTVShows(Callback<Movie.MovieResult> cb);

    @GET("/tv/top_rated")
    void getTopRatedTVShows(Callback<Movie.MovieResult> cb);

    @GET("/search/movie")
    void getSearchMovie(Callback<Movie.MovieResult> cb);

}
