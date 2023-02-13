package com.dct.androidexam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.dct.androidexam.R;
import com.dct.androidexam.network.VolleyRestApi;
import com.dct.util.Constant;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class MoviesDetailsPage extends AppCompatActivity {

    TextView header;
    ImageView imgBack,Play;
    String movieId = "";
    ImageView itemIV, backDropImg;
    TextView itemNameTv, itemOverView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        movieId = String.valueOf(getIntent().getIntExtra("movieID", 0));
        init();
        imgBack.setOnClickListener(v -> callPreviousactivity());
        getDetails(movieId);

    }

    public void onBackPressed() {
        super.onBackPressed();
        callPreviousactivity();
    }

    private void callPreviousactivity() {
        finish();
    }

    private void init() {
        imgBack = findViewById(R.id.imgbackbtn);
        header = findViewById(R.id.txtheader);
        itemNameTv = findViewById(R.id.itemNameTv);
        backDropImg = findViewById(R.id.backDropImg);
        itemIV = findViewById(R.id.itemIV);
        itemOverView = findViewById(R.id.itemOverView);
        Play = findViewById(R.id.Play);
        header.setText("Movies Details");

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PlayMovies.class));
            }
        });

    }


    public void getDetails(String movieId) {
        String path = Constant.MovieDetailsURL + movieId;

        String uri = String.format(path + "?api_key=%1$s", Constant.apiKey);

        VolleyRestApi volleyRestApi = new VolleyRestApi();
        volleyRestApi.volleyWebservice(getApplicationContext(), uri, Constant.apiKey, new VolleyRestApi.VolleyCallback() {
            @Override
            public void onSuccess(Object result) {

                String details = result.toString();
                try {
                    JSONObject jsonObject = new JSONObject(details);

                    String title = jsonObject.getString("original_title");
                    String overview = jsonObject.getString("overview");
                    String poster_path = jsonObject.getString("poster_path");
                    String backdrop_path = jsonObject.getString("backdrop_path");
                    itemNameTv.setText(title);
                    itemOverView.setText(overview);
                    setImage(poster_path, backdrop_path);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                // show error details

            }
        });
    }

    private void setImage(String strPoster, String strbackdrop) {

        String path = Constant.URL + strPoster;
        String path1 = Constant.URL + strbackdrop;
        Picasso.with(getApplicationContext()).load(path).into(itemIV);
        Picasso.with(getApplicationContext()).load(path1).into(backDropImg);
    }
}

