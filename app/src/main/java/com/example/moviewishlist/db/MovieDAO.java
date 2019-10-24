package com.example.moviewishlist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moviewishlist.db.MovieContract;
import com.example.moviewishlist.db.MovieHelper;
import com.example.moviewishlist.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private static MovieHelper mHelper;
    private static int size;

    public MovieDAO(Context context){
        mHelper = MovieHelper.getInstance(context);
    }


    public List<Movie> listMovies(){
        String[] columns = {MovieContract.MovieColumns.MOVIE_ID};
        List<Movie> movies = new ArrayList<>();

        SQLiteDatabase movieDB = mHelper.getReadableDatabase();
        try(Cursor c = movieDB.query(
                MovieContract.TABLE_NAME,columns,null,null,null, null, MovieContract.MovieColumns.MOVIE_ID)){
            if (c.moveToFirst()){
                do {
                    Movie m = new Movie();
                    m.setId(c.getInt(c.getColumnIndex(MovieContract.MovieColumns.MOVIE_ID)));
                    movies.add(m);
                }while (c.moveToNext());
            }
        }
        return movies;
    }

    public void saveToList(int movieId){
        SQLiteDatabase movieDB = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieColumns.MOVIE_ID,movieId);
        movieDB.insert(MovieContract.TABLE_NAME,null,values);
        size++;
    }
    public void deleteFromList(Integer movieId){
        SQLiteDatabase movieDB = mHelper.getWritableDatabase();
        movieDB.delete(
                MovieContract.TABLE_NAME,
                MovieContract.MovieColumns.MOVIE_ID+"=?", new String[]{movieId+""}
        );
        size--;
    }
}
