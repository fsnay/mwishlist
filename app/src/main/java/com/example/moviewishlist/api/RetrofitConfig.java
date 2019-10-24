package com.example.moviewishlist.api;

import android.util.Log;

import com.example.moviewishlist.model.Movie;
import com.example.moviewishlist.model.MovieList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private static final String apiKey = "7dab1df51190cb00e691ed7f654d36d9";
    private static RetrofitConfig retrofitConfig;
    private MovieService movieService;

    private RetrofitConfig(MovieService movieService){
        this.movieService = movieService;
    }

    public static RetrofitConfig getInstance(){
        if(retrofitConfig == null){
            Retrofit rf = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            retrofitConfig = new RetrofitConfig(rf.create(MovieService.class));
        }
        return retrofitConfig;
    }

    public void getPopularMovies(final OnAPIListCallback callback)  {

        Call<MovieList> call = movieService.listPopularMovies(apiKey);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if(response.isSuccessful()){
                    Log.w("GETSuccess","Popular movies");
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.w("GETFailure","Error getting popular movies",t);
                callback.onError();
            }
        });
    }

    public void searchMovie(final String query,final OnAPIListCallback callback) {
        Call<MovieList> call = movieService.searchMovie(apiKey,query);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    Log.w("GETSuccess", "Search movie");
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.w("GETFailure", "Error searching movie", t);
                callback.onError();
            }
        });

    }

    public void getMovieInfo(int id, final OnAPICallback callback){
        Call<Movie> call = movieService.searchMovieById(id,apiKey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Log.w("GETSuccess", "Movie details");
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                callback.onError();
                Log.w("GETFailure", "Error getting details", t);
            }
        });
    }

    public void getUpcomingMovies(final OnAPIListCallback callback){
        Call<MovieList> call = movieService.listUpcomingMovies(apiKey);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    Log.w("GETSuccess", "Upcoming movies");
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.w("GETFailure", "Error getting upcoming movies", t);
                callback.onError();
            }
        });
    }

    public void getTopRatedMovies(final OnAPIListCallback callback){
        Call<MovieList> call = movieService.listTopRatedMovies(apiKey);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    Log.w("GETSuccess", "Upcoming movies");
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.w("GETFailure", "Error getting upcoming movies", t);
                callback.onError();
            }
        });
    }

}
