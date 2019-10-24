package com.example.moviewishlist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviewishlist.R;
import com.example.moviewishlist.activity.DetailMovieActivity;
import com.example.moviewishlist.activity.MainActivity;
import com.example.moviewishlist.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private ArrayList<Movie> movies;
    Context c;

    public MovieAdapter(List<Movie> movies){
        if(movies == null){
            this.movies = new ArrayList<>();
        }
        else{
            this.movies = new ArrayList<>(movies);
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView releaseDate;
        TextView voteAverage;
        ImageView poster;
        MovieClickListener mcl;

        public MovieViewHolder(View v){
            super(v);
            title = v.findViewById(R.id.title);
            releaseDate = v.findViewById(R.id.releaseDate);
            voteAverage = v.findViewById(R.id.voteAverage);
            poster = v.findViewById(R.id.poster);

        }
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View movieItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item_list,viewGroup,false);
        c = viewGroup.getContext();
        return new MovieViewHolder(movieItemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        final Movie m = movies.get(i);
        movieViewHolder.title.setText(m.getTitle());
        movieViewHolder.releaseDate.setText(m.getReleaseDate());
        movieViewHolder.voteAverage.setText(m.getVoteAverage());
        Picasso.get().load(c.getString(R.string.mListPosterBaseURL)+m.getPosterPath()).into(movieViewHolder.poster);

       movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             Intent i  = new Intent(v.getContext(), DetailMovieActivity.class);
             i.putExtra("movieListId",m.getId());
               v.getContext().startActivity(i);
           }
       });
       movieViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               ((MainActivity)c).addToWishlist(m);
               return true;
           }
       });
    }
    @Override
    public int getItemCount() {
        int size = 0;
        if(movies != null && movies.size()> 0){
            size = movies.size();
        }
        return size;
    }

    @Override
    public void onViewRecycled(MovieViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public void setMovies(List<Movie> movies){
        if(movies == null || movies.isEmpty()){
          this.movies.clear();
        }
        else{
            this.movies = (ArrayList<Movie>) movies;
        }
       notifyDataSetChanged();
    }
}
