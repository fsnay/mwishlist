package com.example.moviewishlist.api;

import com.example.moviewishlist.model.Movie;
import com.example.moviewishlist.model.MovieList;

public interface OnAPIListCallback {
    void onSuccess(MovieList movies);
    void onError();
}
