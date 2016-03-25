package me.seatech.movienama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    //defining the keys
    public static final String TITLE = "title" ;
    public static final String RATING = "rating" ;
    public static final String OVERVIEW = "overview" ;
    public static final String POSTER_PATH = "path" ;
    public static final String RELEASE_DATE = "date" ;

    private static final String LOG_TAG = DetailActivity.class.getSimpleName() ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent() ;




        Bundle arguments = new Bundle() ;
        arguments.putString(POSTER_PATH,intent.getStringExtra(POSTER_PATH));
        arguments.putString(OVERVIEW,intent.getStringExtra(OVERVIEW));
        arguments.putString(RELEASE_DATE,intent.getStringExtra(RELEASE_DATE));
        arguments.putString(TITLE,intent.getStringExtra(TITLE));
        arguments.putString(RATING,intent.getDoubleExtra(RATING,0.0)+"");

        DetailFragment detailFragment = new DetailFragment() ;
        detailFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_detail_container, detailFragment)
                .commit();

    }

}
