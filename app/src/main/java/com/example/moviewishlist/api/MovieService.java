package com.example.moviewishlist.api;

import com.example.moviewishlist.model.Movie;
import com.example.moviewishlist.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("search/movie")
    Call<MovieList> searchMovie(@Query("api_key") String apiKey, @Query("query") String movie);

    @GET("movie/{movie_id}")
    Call<Movie> searchMovieById(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieList> listPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieList> listUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieList> listTopRatedMovies(@Query("api_key") String apiKey);
}
