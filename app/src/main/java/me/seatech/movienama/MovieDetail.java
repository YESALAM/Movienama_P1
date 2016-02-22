package me.seatech.movienama;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.seatech.movienama.util.Api;

public class MovieDetail extends AppCompatActivity {

    //defining the keys
    public static final String TITLE = "title" ;
    public static final String RATING = "rating" ;
    public static final String OVERVIEW = "overview" ;
    public static final String POSTER_PATH = "path" ;
    public static final String RELEASE_DATE = "date" ;

    private static final String LOG_TAG = MovieDetail.class.getSimpleName() ;

    private TextView titleTextView ;
    private TextView releaseTextView ;
    private TextView ratingTextView ;
    private TextView overviewTextView ;
    private ImageView posterImageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent() ;


        posterImageView = (ImageView) findViewById(R.id.posterImage);
        titleTextView = (TextView) findViewById(R.id.title_tv) ;
        releaseTextView = (TextView) findViewById(R.id.release_tv) ;
        ratingTextView = (TextView) findViewById(R.id.rating_tv) ;
        overviewTextView = (TextView) findViewById(R.id.overview_tv) ;

        String url = Api.BASE_URL_IMAGE+Api.SIZE_W342+intent.getStringExtra(POSTER_PATH) ;
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.movie_reel_clip_art)
                .error(R.drawable.movie_reel_clip_art)
                .into(posterImageView);

        titleTextView.setText(intent.getStringExtra(TITLE));
        ratingTextView.setText(intent.getDoubleExtra(RATING,0.0)+"/10");
        overviewTextView.setText(intent.getStringExtra(OVERVIEW));

        String dateString = intent.getStringExtra(RELEASE_DATE);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM", Locale.ENGLISH) ;
        try {
            Date date = dateFormat.parse(dateString) ;
            DateFormat simpleDateFormat = new SimpleDateFormat("MMM  yyyy",Locale.ENGLISH) ;
            releaseTextView.setText(simpleDateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
