package com.danielburgnerjr.movietvdb;

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

    @GET("/movie/{id}/videos")
    void getMovieVideos(Callback<Movie.MovieResult> cb);

    @GET("/movie/{id}/reviews")
    void getMovieReviews(Callback<Movie.MovieResult> cb);

    @GET("/tv/popular")
    void getPopularTVShows(Callback<TV.TVResult> cb);

    @GET("/tv/top_rated")
    void getTopRatedTVShows(Callback<TV.TVResult> cb);

    @GET("/tv/{id}/videos")
    void getTVVideos(Callback<TV.TVResult> cb);

    @GET("/tv/{id}/reviews")
    void getTVReviews(Callback<TV.TVResult> cb);

    @GET("/search/movie")
    void getSearchMovie(Callback<Movie.MovieResult> cb);

}
