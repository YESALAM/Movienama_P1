package me.seatech.movienama;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.seatech.movienama.adapter.ViewPagerAdapter;
import me.seatech.movienama.schemas.Movie;
import me.seatech.movienama.util.Api;

public class DetailActivity extends AppCompatActivity {


    private static final String LOG_TAG = DetailActivity.class.getSimpleName() ;
    @Bind(R.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout toolbarLayout ;
    @Bind(R.id.backdrop_poster)
    ImageView backdropPosterIv;
    @Bind(R.id.pager_title_strip)
    PagerTitleStrip pagerTitleStrip;
    @Bind(R.id.viewPager)
    ViewPager viewPager ;
    @Bind(R.id.scroll)
    NestedScrollView nestedScrollView;
    ViewPagerAdapter pagerAdapter;


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

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),movie);
        viewPager.setAdapter(pagerAdapter);
        nestedScrollView.setFillViewport(true);

        toolbarLayout.setTitle(movie.getTitle());
        String url = Api.BASE_URL_IMAGE+Api.SIZE_W342+ movie.getBackdropPath() ;
        Picasso.with(this).load(url).into(backdropPosterIv);

      /*  Bundle arguments = new Bundle() ;
        arguments.putParcelable("movie", movie);

        DetailFragment detailFragment = new DetailFragment() ;
        detailFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_detail_container, detailFragment)
                .commit();*/

    }

}
