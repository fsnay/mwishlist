package com.example.moviewishlist.db;

import android.provider.BaseColumns;

public final class MovieContract {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "MyMovieList";
    public static final String TABLE_NAME = "movies";


    public static final class MovieColumns implements BaseColumns {
        public static final String MOVIE_ID = "movieId";
    }
}
