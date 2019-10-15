package com.danielburgnerjr.movietvdb.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MovieTVDBContentProvider extends ContentProvider {
    public static final int MOVIES = 100;
    public static final int MOVIE_ID = 101;
    public static final int TVS = 200;
    public static final int TV_ID = 201;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    MovieTVDbHelper movieDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieTVDBContract.AUTHORITY, MovieTVDBContract.PATH_ENTRY, MOVIES);
        uriMatcher.addURI(MovieTVDBContract.AUTHORITY, MovieTVDBContract.PATH_ENTRY + "/*", MOVIE_ID);

        uriMatcher.addURI(MovieTVDBContract.AUTHORITY, MovieTVDBContract.PATH_ENTRY, TVS);
        uriMatcher.addURI(MovieTVDBContract.AUTHORITY, MovieTVDBContract.PATH_ENTRY + "/*", TV_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieTVDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case MOVIES:
                retCursor = db.query(MovieTVDBContract.MovieEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        MovieTVDBContract.MovieEntry.COLUMN_TIMESTAMP);
                break;
            case TVS:
                retCursor = db.query(MovieTVDBContract.TVEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        MovieTVDBContract.TVEntry.COLUMN_TIMESTAMP);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        long id;
        switch (match) {
            case MOVIES:
                id = db.insert(MovieTVDBContract.MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieTVDBContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case TVS:
                id = db.insert(MovieTVDBContract.TVEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieTVDBContract.TVEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int moviesDeleted = 0; // starts as 0
        int tvsDeleted = 0; // starts as 0
        String id;
        int deletedRecords = 0;

        switch (match) {
            case MOVIE_ID:
                id = uri.getPathSegments().get(1);
                moviesDeleted = db.delete(MovieTVDBContract.MovieEntry.TABLE_NAME, "id=?", new String[]{id});
                break;
            case TV_ID:
                id = uri.getPathSegments().get(1);
                tvsDeleted = db.delete(MovieTVDBContract.TVEntry.TABLE_NAME, "id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        switch (match) {
            case MOVIE_ID:
                if (moviesDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                deletedRecords = moviesDeleted;
            case TV_ID:
                if (tvsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                deletedRecords = tvsDeleted;
        }
        return deletedRecords;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
