package me.seatech.movienama;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import me.seatech.movienama.adapter.GridAdapter;
import me.seatech.movienama.scheme.*;
import me.seatech.movienama.scheme.Result;
import me.seatech.movienama.util.Api;
import me.seatech.movienama.util.TmdbAPi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<Movies>,AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private final String LOG_TAG = "MainActivity" ;
    private Toolbar toolbar ;
    private Spinner spinner ;
    private GridView gridView ;
    private ProgressBar progressBar ;
    private Retrofit retrofit ;
    private TmdbAPi tmdbAPi ;
    private ArrayAdapter<CharSequence> adapter;
    private  List<Result> results ;
    private GridAdapter gridAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        gridView = (GridView) findViewById(R.id.gridView) ;
        gridView.setOnItemClickListener(this);
        gridView.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        setSpinner();

        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbAPi = retrofit.create(TmdbAPi.class) ;

    }


    private void setSpinner(){
        spinner.setOnItemSelectedListener(this);
        adapter = ArrayAdapter.createFromResource(this,R.array.sorting_param,android.R.layout.simple_spinner_item) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onResponse(Call<Movies> call, Response<Movies> response) {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        results = response.body().getResults() ;
        gridAdapter = new GridAdapter(this,results);
        gridView.setAdapter(gridAdapter);

    }

    @Override
    public void onFailure(Call<Movies> call, Throwable t) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!checkConnectivity()){
            Toast.makeText(this,"Connection Unavilable !",Toast.LENGTH_SHORT).show();
            return;
        }
        CharSequence sequence = adapter.getItem(position) ;
        if(sequence.equals("High Rating")){
            Call<Movies> ratingCall = tmdbAPi.loadHighRated(Api.API_KEY) ;
            ratingCall.enqueue(this);
        } else {
            Call<Movies> popularCall = tmdbAPi.loadPopular(Api.API_KEY) ;
            popularCall.enqueue(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Result movie_clicked = (Result) gridAdapter.getItem(position);

        Intent intent = new Intent(this,MovieDetail.class) ;
        intent.putExtra(MovieDetail.TITLE, movie_clicked.getOriginalTitle());
        intent.putExtra(MovieDetail.POSTER_PATH, movie_clicked.getPosterPath());
        intent.putExtra(MovieDetail.OVERVIEW, movie_clicked.getOverview());
        intent.putExtra(MovieDetail.RATING, movie_clicked.getVoteAverage());
        intent.putExtra(MovieDetail.RELEASE_DATE, movie_clicked.getReleaseDate());
        startActivity(intent);
    }

    private boolean checkConnectivity(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected ;
    }
}
