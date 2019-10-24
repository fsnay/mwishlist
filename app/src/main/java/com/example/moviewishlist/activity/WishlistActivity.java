package com.example.moviewishlist.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviewishlist.R;
import com.example.moviewishlist.adapters.MWishlistAdapter;
import com.example.moviewishlist.api.OnAPICallback;
import com.example.moviewishlist.api.RetrofitConfig;
import com.example.moviewishlist.db.MovieDAO;
import com.example.moviewishlist.model.Movie;

import java.util.List;

public class WishlistActivity extends AppCompatActivity{

    private RecyclerView movieWishlist;
    private MWishlistAdapter mwAdapter;
    private RetrofitConfig retrofitConfig;
    private MovieDAO movieDAO;
    private TextView emptyWishlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_wishlist);

        movieWishlist = findViewById(R.id.movieWishlist);
        emptyWishlist = findViewById(R.id.emptyWishlist);
        mwAdapter = new MWishlistAdapter(null);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        movieWishlist.setLayoutManager(llm);
        movieDAO = new MovieDAO(this);
        movieWishlist.setAdapter(mwAdapter);
        retrofitConfig = RetrofitConfig.getInstance();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        List<Movie> moviesDB = movieDAO.listMovies();

        if(moviesDB!=null && !moviesDB.isEmpty()){
            requestMovies(moviesDB);
            emptyWishlist.setVisibility(View.GONE);
            movieWishlist.setVisibility(View.VISIBLE);
        }
        else{
            emptyWishlist.setVisibility(View.VISIBLE);
            movieWishlist.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() != R.id.wishlist){
            Intent i = new Intent(WishlistActivity.this,MainActivity.class);
            i.putExtra("menuItemId",item.getItemId());
            startActivity(i);
        }
        return true;
    }

    private void requestMovies(List<Movie> movies){
        for(final Movie m: movies){
            final int id = m.getId();
            retrofitConfig.getMovieInfo(id, new OnAPICallback() {
                @Override
                public void onSuccess(Movie movie) {
                    movie.setId(id);
                    mwAdapter.addMovie(movie);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    public void removeFromWishlist(final View v, final Movie m){

        movieDAO.deleteFromList(m.getId());
        mwAdapter.removeMovie(m);
        movieWishlist.setAdapter(mwAdapter);

        if(mwAdapter.getItemCount() == 0){
            emptyWishlist.setVisibility(View.VISIBLE);
            movieWishlist.setVisibility(View.GONE);
        }
        notifyMovie();
    }

    private void notifyMovie(){
        int totalMovies = mwAdapter.getItemCount();

        if(totalMovies>0){
            Notification.Builder notification =
                    new Notification.Builder(this)
                            .setSmallIcon(R.drawable.ic_baseline_movie)
                            .setContentTitle("Hurry up!!")
                            .setContentText("You still have "+ totalMovies+" movie(s) to watch");
            Intent i = new Intent(this,WishlistActivity.class);
            PendingIntent content = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(content);

            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(0,notification.build());
        }
        else{
            Notification.Builder notification =
                    new Notification.Builder(this)
                            .setSmallIcon(R.drawable.ic_baseline_movie)
                            .setContentTitle("It's time for a new round!")
                            .setContentText("Any movie interesting you?");
            Intent i = new Intent(this,WishlistActivity.class);
            PendingIntent content = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(content);

            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(0,notification.build());
        }
    }
}
