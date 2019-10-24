package com.example.moviewishlist.api;

import com.example.moviewishlist.model.Movie;

public interface OnAPICallback {
    void onSuccess(Movie movie);
    void onError();
}
