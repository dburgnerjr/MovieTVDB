package com.danielburgnerjr.movietvdb.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieTVDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "popularmovie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieTVDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieTVDBContract.MovieEntry.TABLE_NAME + " (" +
                //MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTVDBContract.MovieEntry.COLUMN_NAME_ID + " TEXT NOT NULL PRIMARY KEY, " +
                MovieTVDBContract.MovieEntry.COLUMN_NAME_ORIGINALTITLE + " TEXT NOT NULL, " +
                MovieTVDBContract.MovieEntry.COLUMN_NAME_OVERVIEW + " TEXT NOT NULL, " +
                MovieTVDBContract.MovieEntry.COLUMN_NAME_POSTERPATH + " TEXT NOT NULL, " +
                MovieTVDBContract.MovieEntry.COLUMN_NAME_BACKDROP + " TEXT NOT NULL, " +
                MovieTVDBContract.MovieEntry.COLUMN_NAME_RELEASEDATE + " TEXT NOT NULL, " +
                MovieTVDBContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE + " TEXT NOT NULL, " +
                MovieTVDBContract.MovieEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

        final String SQL_CREATE_TV_TABLE = "CREATE TABLE " +
                MovieTVDBContract.TVEntry.TABLE_NAME + " (" +
                //MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTVDBContract.TVEntry.COLUMN_NAME_ID + " TEXT NOT NULL PRIMARY KEY, " +
                MovieTVDBContract.TVEntry.COLUMN_NAME_ORIGINALTITLE + " TEXT NOT NULL, " +
                MovieTVDBContract.TVEntry.COLUMN_NAME_OVERVIEW + " TEXT NOT NULL, " +
                MovieTVDBContract.TVEntry.COLUMN_NAME_POSTERPATH + " TEXT NOT NULL, " +
                MovieTVDBContract.TVEntry.COLUMN_NAME_BACKDROP + " TEXT NOT NULL, " +
                MovieTVDBContract.TVEntry.COLUMN_NAME_RELEASEDATE + " TEXT NOT NULL, " +
                MovieTVDBContract.TVEntry.COLUMN_NAME_VOTEAVERAGE + " TEXT NOT NULL, " +
                MovieTVDBContract.TVEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";

        db.execSQL(SQL_CREATE_TV_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + MovieTVDBContract.MovieEntry.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + MovieTVDBContract.TVEntry.TABLE_NAME);
        onCreate(db);

    }

}
