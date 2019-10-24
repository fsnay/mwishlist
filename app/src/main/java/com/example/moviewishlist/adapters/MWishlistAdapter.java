package com.example.moviewishlist.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviewishlist.R;
import com.example.moviewishlist.activity.DetailMovieActivity;
import com.example.moviewishlist.activity.WishlistActivity;
import com.example.moviewishlist.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MWishlistAdapter extends RecyclerView.Adapter<MWishlistAdapter.MWViewHolder> {

    private ArrayList<Movie> wishlist;
    private Context c;

    public MWishlistAdapter(ArrayList<Movie>wishlist){
        if(wishlist== null){
            this.wishlist= new ArrayList<>();
        }
        else{
            this.wishlist = new ArrayList<>(wishlist);
        }
    }

    public class MWViewHolder extends RecyclerView.ViewHolder{
        TextView titleWish;
        TextView releaseDateWish;
        TextView voteAverageWish;
        ImageView posterWish;
//        FloatingActionButton watched;

        public MWViewHolder(View v){
            super(v);
            titleWish = v.findViewById(R.id.titleWish);
            releaseDateWish = v.findViewById(R.id.releaseDateWish);
            voteAverageWish= v.findViewById(R.id.voteAverageWish);
            posterWish = v.findViewById(R.id.posterWish);
//            watched = v.findViewById(R.id.watchedBT);
        }
    }
    @NonNull
    @Override
    public MWViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View wishMovieView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_movie_item,viewGroup,false);
        c = viewGroup.getContext();
        return new MWishlistAdapter.MWViewHolder(wishMovieView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MWViewHolder mwViewHolder, int i) {
        final Movie m = wishlist.get(i);
        mwViewHolder.titleWish.setText(m.getTitle());
        mwViewHolder.releaseDateWish.setText(m.getReleaseDate());
        mwViewHolder.voteAverageWish.setText(m.getVoteAverage());
        Picasso.get().load(c.getString(R.string.mListPosterBaseURL)+m.getPosterPath()).into(mwViewHolder.posterWish);

        mwViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(v.getContext(), DetailMovieActivity.class);
                i.putExtra("movieListId",m.getId());
                v.getContext().startActivity(i);
            }
        });

        mwViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final String[] items = {"Remove", "Cancel"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(c);

                dialog.setTitle("Did you watch this?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == DialogInterface.BUTTON_POSITIVE){
                            ((WishlistActivity)c).removeFromWishlist(v,m);
                        }
                    }
                });
                dialog.setNegativeButton("Not yet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == DialogInterface.BUTTON_NEGATIVE){
                           dialog.dismiss();
                        }
                    }
                });
                dialog.create();
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(wishlist != null && wishlist.size()> 0){
            size = wishlist.size();
        }
        return size;
    }

    public void onViewRecycled(MWishlistAdapter.MWViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public void setMovies(List<Movie> movies){
        if(movies == null || movies.isEmpty()){
            this.wishlist.clear();
        }
        else{
            this.wishlist = (ArrayList<Movie>) movies;
        }
        notifyDataSetChanged();
    }

    public void addMovie(Movie m){
        if(wishlist != null){
            wishlist.add(m);
        }
        else{
            wishlist = new ArrayList<>();
            wishlist.add(m);
        }
        notifyItemInserted(wishlist.indexOf(m));
    }

    public void removeMovie(Movie m){
        int position = 0;
        if(wishlist != null && !wishlist.isEmpty()){
            position = wishlist.indexOf(m);
            wishlist.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<Movie> getMovies(){
        return wishlist;
    }

}
