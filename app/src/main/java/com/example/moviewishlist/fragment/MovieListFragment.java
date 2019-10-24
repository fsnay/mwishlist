package com.example.moviewishlist.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviewishlist.R;
import com.example.moviewishlist.adapters.MovieAdapter;
import com.example.moviewishlist.api.OnAPIListCallback;
import com.example.moviewishlist.api.RetrofitConfig;
import com.example.moviewishlist.db.MovieDAO;
import com.example.moviewishlist.model.Movie;
import com.example.moviewishlist.model.MovieList;

import java.util.List;

public class MovieListFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private TextView emptyMovieList;
    private RecyclerView movieRecycleView;
    private MovieAdapter mAdapter;
    private MovieDAO movieDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.movie_list,container,false);
        movieRecycleView = v.findViewById(R.id.movieRecycleView);
        emptyMovieList = v.findViewById(R.id.emptyMovieList);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        movieRecycleView.setLayoutManager(llm);

        mAdapter = new MovieAdapter(null);
        retrofitConfig = RetrofitConfig.getInstance();
        movieDAO = new MovieDAO(getContext());

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPopularMovies();
    }

    public void searchMovie(String query){
        if(query == null || query.isEmpty()){
            getPopularMovies();
        }
        else{
            retrofitConfig.searchMovie(query,new OnAPIListCallback() {
                @Override
                public void onSuccess(MovieList movies) {
                    if(!movies.getResults().isEmpty()){
                        mAdapter.setMovies(movies.getResults());
                        movieRecycleView.setAdapter(mAdapter);
                        emptyMovieList.setVisibility(View.GONE);
                        movieRecycleView.setVisibility(View.VISIBLE);
                    }
                    else{
                        emptyMovieList.setVisibility(View.VISIBLE);
                        movieRecycleView.setVisibility(View.GONE);
                    }


                }

                @Override
                public void onError() {
                    emptyMovieList.setVisibility(View.VISIBLE);
                    movieRecycleView.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getPopularMovies(){
        retrofitConfig.getPopularMovies(new OnAPIListCallback() {
            @Override
            public void onSuccess(MovieList movies) {
                mAdapter.setMovies(movies.getResults());
                movieRecycleView.setAdapter(mAdapter);
                Log.w("GETSuccess","retornou populares");
            }

            @Override
            public void onError() {
                Log.w("GETFailure","erro no callback de populares");

            }
        });
    }

    public void getUpcomingMovies() {
        retrofitConfig.getUpcomingMovies(new OnAPIListCallback() {
            @Override
            public void onSuccess(MovieList movies) {
                mAdapter.setMovies(movies.getResults());
                movieRecycleView.setAdapter(mAdapter);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void getTopRatedMovies() {
        retrofitConfig.getTopRatedMovies(new OnAPIListCallback() {
            @Override
            public void onSuccess(MovieList movies) {
                mAdapter.setMovies(movies.getResults());
                movieRecycleView.setAdapter(mAdapter);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void addToWishlist(Movie m) throws Exception {


        List<Movie> moviesInWishlist = movieDAO.listMovies();
        boolean inWishlist = false;
        for(int i = 0;i<moviesInWishlist.size();i++){
            if(moviesInWishlist.get(i).getId() == m.getId()){
                inWishlist = true;
                break;
            }
        }
        if(!inWishlist){
            movieDAO.saveToList(m.getId());
        }
        else {
            throw new Exception("This movie already is in your list!");
        }
    }
}
