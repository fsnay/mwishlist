package com.example.moviewishlist.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviewishlist.R;
import com.example.moviewishlist.api.OnAPICallback;
import com.example.moviewishlist.api.RetrofitConfig;
import com.example.moviewishlist.model.Movie;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;

public class DetailMovieActivity extends AppCompatActivity {

    private TextView titleDetail;
    private TextView releaseDetail;
    private TextView overviewDetail;
    private TextView ratingDetail;
    private TextView runtimeDetail;
    private TextView statusDetail;
    private ImageView posterDetail;

    private int movieId;

    RetrofitConfig retrofitConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        titleDetail = findViewById(R.id.title_detail);
        releaseDetail = findViewById(R.id.release_detail);
        overviewDetail = findViewById(R.id.overview_detail);
        ratingDetail = findViewById(R.id.voteAverageDetail);
        runtimeDetail = findViewById(R.id.runtime_detail);
        posterDetail = findViewById(R.id.poster_detail);
        statusDetail = findViewById(R.id.statusDetail);

        retrofitConfig = RetrofitConfig.getInstance();

        movieId = getIntent().getIntExtra("movieListId",0);

        if(movieId == 0){
            Log.w("MovieID", "movie id = 0 ");
            finish();
        }
    }

    @Override
    protected void onResume() {
        retrofitConfig.getMovieInfo(movieId, new OnAPICallback() {
            @Override
            public void onSuccess(Movie movie) {
                titleDetail.setText(movie.getTitle());
                releaseDetail.setText(movie.getReleaseDate());
                overviewDetail.setText(movie.getOverview());
                ratingDetail.setText(movie.getVoteAverage());
                runtimeDetail.setText(movie.getRuntime() + " min");
                statusDetail.setText(movie.getStatus());
                Picasso.get().load(getString(R.string.mDetailPosterBaseURL)+movie.getPosterPath()).into(posterDetail);
            }

            @Override
            public void onError() {

            }
        });
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() != R.id.wishlist){
            Intent i = new Intent(DetailMovieActivity.this,MainActivity.class);
            i.putExtra("menuItemId",item.getItemId());
            startActivity(i);
        }
        return true;
    }
}
