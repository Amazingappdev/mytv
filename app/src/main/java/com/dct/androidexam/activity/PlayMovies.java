package com.dct.androidexam.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dct.androidexam.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;


public class PlayMovies extends AppCompatActivity {


    ExoPlayer exoPlayer;
    PlayerView simpleExoPlayer;
    String title="";
    TextView txtTitle;

    String url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    int appNameStringRes = R.string.app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_movies);
        title=getIntent().getStringExtra("title");

        init();


        exoPlayer = new ExoPlayer.Builder(getApplicationContext()).build();

        simpleExoPlayer.setPlayer(exoPlayer); // attach surface to the view


        MediaItem mediaItem = MediaItem.fromUri(url);
        exoPlayer.addMediaItem(mediaItem);
// Prepare exoplayer
        exoPlayer.prepare();
// Play media when it is ready
        exoPlayer.setPlayWhenReady(true);

    }

    public void onBackPressed() {
        super.onBackPressed();
        callPreviousactivity();
    }

    private void callPreviousactivity() {
        if (exoPlayer != null) {
            exoPlayer.release();
            finish();
        }
        else {
            finish();
        }

    }
    private void init() {
        simpleExoPlayer = findViewById(R.id.exoplayerView);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

    }


}

