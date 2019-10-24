package com.example.moviewishlist.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviewishlist.R;
import com.example.moviewishlist.fragment.MovieListFragment;
import com.example.moviewishlist.model.Movie;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton searchBT;
    private EditText searchBar;
    private MovieListFragment mlf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBar = findViewById(R.id.searchBar);
        searchBT = findViewById(R.id.searchBt);
        mlf = (MovieListFragment) getSupportFragmentManager().findFragmentById(R.id.movieListFragment);

        int menuItemId = getIntent().getIntExtra("menuItemId",0);
        switch (menuItemId){
            case R.id.popularMovies:
                goToPopular();
                break;
            case R.id.upcomingMovies:
                goToUpcoming();
                break;
            case R.id.topRatedMovies:
                goToTopRatedMovies();
                break;
            default:
                goToPopular();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popularMovies:
                goToPopular();
                break;
            case R.id.upcomingMovies:
                goToUpcoming();
                break;
            case R.id.topRatedMovies:
                goToTopRatedMovies();
                break;
            case R.id.wishlist:
                Intent i = new Intent(MainActivity.this,WishlistActivity.class);
                startActivity(i);
                break;
            default:
                goToPopular();
        }
        return true;
    }

    public void searchMovie(View view) {
        mlf.searchMovie(searchBar.getText().toString());
    }
    public void addToWishlist(Movie m){
       try{
           mlf.addToWishlist(m);
           Toast.makeText(this,"'"+m.getTitle()+"' added to your Movie WishList!", Toast.LENGTH_LONG).show();
       }
       catch(Exception e){
           Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
       }
    }

    public void goToUpcoming() {
        mlf.getUpcomingMovies();
    }

    public void goToPopular() {
        mlf.getPopularMovies();
    }
    public void goToTopRatedMovies(){
        mlf.getTopRatedMovies();
    }
}
