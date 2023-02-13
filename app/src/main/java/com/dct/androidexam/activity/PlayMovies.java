package com.dct.androidexam.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dct.androidexam.R;


public class PlayMovies extends AppCompatActivity {


    View simpleExoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_movies);

        init();


    }

    public void onBackPressed() {
        super.onBackPressed();
        callPreviousactivity();
    }

    private void callPreviousactivity() {
        finish();
    }

    private void init() {
        simpleExoPlayer = findViewById(R.id.exoplayerView);

    }


}

