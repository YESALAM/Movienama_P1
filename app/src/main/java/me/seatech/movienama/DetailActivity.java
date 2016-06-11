package me.seatech.movienama;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.seatech.movienama.schemas.Movie;
import me.seatech.movienama.schemas.Trailer;
import me.seatech.movienama.schemas.TrailerResult;
import me.seatech.movienama.util.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity  {


    private static final String LOG_TAG = DetailActivity.class.getSimpleName() ;
    @Bind(R.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout toolbarLayout ;
    @Bind(R.id.backdrop_poster)
    ImageView backdropPosterIv;

    ArrayList<me.seatech.movienama.schemas.Trailer> trailerList ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent() ;
        Movie movie = intent.getParcelableExtra("movie");


        toolbarLayout.setTitle(movie.getTitle());
        String url = Api.BASE_URL_IMAGE+Api.SIZE_W342+ movie.getBackdropPath() ;
        Picasso.with(this).load(url).into(backdropPosterIv);
        if(findViewById(R.id.movie_detail_container) != null){

            if(savedInstanceState != null){
                return;
            }

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container,detailFragment).commit() ;
        }


    }


}
