package com.example.moviewishlist.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moviewishlist.R;

public class SplashActivity extends AppCompatActivity {
    private final int TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(splashIntent);
                finish();
            }
        },TIME_OUT);
    }
}
