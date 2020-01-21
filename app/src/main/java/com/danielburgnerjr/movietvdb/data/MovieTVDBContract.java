package com.danielburgnerjr.movietvdb.data;

import android.net.Uri;
import android.provider.BaseColumns;

@SuppressWarnings("WeakerAccess")
public class MovieTVDBContract {

    private MovieTVDBContract(){}

    public static final String AUTHORITY = "com.danielburgnerjr.movietvdb";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String MOVIE_ENTRY = "movie";
    public static final String TV_ENTRY = "tv";

    /* Define table and content */
    public static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_ENTRY).build();

        public static final String TABLE_NAME = "favorite_movie";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ORIGINALTITLE = "originalTitle";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_POSTERPATH = "posterPath";
        public static final String COLUMN_NAME_BACKDROP = "backdrop";
        public static final String COLUMN_NAME_RELEASEDATE = "releaseDate";
        public static final String COLUMN_NAME_VOTEAVERAGE = "voteAverage";
        public static final String COLUMN_TIMESTAMP ="timestamp";
    }

    public static class TVEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TV_ENTRY).build();

        public static final String TABLE_NAME = "favorite_tv";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ORIGINALTITLE = "originalTitle";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_POSTERPATH = "posterPath";
        public static final String COLUMN_NAME_BACKDROP = "backdrop";
        public static final String COLUMN_NAME_RELEASEDATE = "releaseDate";
        public static final String COLUMN_NAME_VOTEAVERAGE = "voteAverage";
        public static final String COLUMN_TIMESTAMP ="timestamp";
    }
}
