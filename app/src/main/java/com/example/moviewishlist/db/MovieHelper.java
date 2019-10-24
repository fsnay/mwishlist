package com.example.moviewishlist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieHelper extends SQLiteOpenHelper {

    private static MovieHelper mh;
    private static String SQL_CREATE =
            String.format("CREATE TABLE %s " +
                            "(" +
                                "%s INTEGER PRIMARY KEY " +
                            ")",
            MovieContract.TABLE_NAME,MovieContract.MovieColumns.MOVIE_ID);

    private MovieHelper(Context context) {
        super(context, MovieContract.DB_NAME, null, MovieContract.DB_VERSION);
    }

    public static MovieHelper getInstance(Context context){
        if (mh == null){
            mh = new MovieHelper(context);
        }
        return mh;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+MovieContract.TABLE_NAME);
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
