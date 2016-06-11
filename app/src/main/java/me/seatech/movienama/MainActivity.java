package me.seatech.movienama;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


import butterknife.Bind;
import butterknife.ButterKnife;
import me.seatech.movienama.schemas.*;
import me.seatech.movienama.schemas.Movie;
import me.seatech.movienama.util.Api;
import me.seatech.movienama.util.TmdbAPi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<MovieResult>,AdapterView.OnItemSelectedListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName() ;

    @Bind(R.id.toolbar)
     Toolbar toolbar ;
    @Bind(R.id.spinner)
    Spinner spinner ;
    private Retrofit retrofit ;
    private TmdbAPi tmdbAPi ;
    private MainFragment mainFragment ;
    private ArrayList<Movie> movies;
    private int choice=0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainFragment= (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        setSupportActionBar(toolbar);
        setSpinner();



        retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        tmdbAPi = retrofit.create(TmdbAPi.class) ;

        Log.e("main ", BuildConfig.API) ;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels ;
        int dp = (width * 160)/ metrics.densityDpi ;
        Log.e("Main"," dp is "+dp) ;

        if(savedInstanceState != null ){
            spinner.setSelection(savedInstanceState.getInt("choice"));
            movies = savedInstanceState.getParcelableArrayList("key");
            mainFragment.refresh(movies);
        }
    }


    private void setSpinner(){
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sorting_param, android.R.layout.simple_spinner_item) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("key", movies);
        outState.putInt("choice",choice);
    }

    @Override
    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
        movies = (ArrayList<Movie>) response.body().getMovies();
        mainFragment.refresh(movies);
    }

    @Override
    public void onFailure(Call<MovieResult> call, Throwable t) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!checkConnectivity()){
            Toast.makeText(this,"Connection Unavilable !",Toast.LENGTH_SHORT).show();
            return;
        }
        choice = position ;
        switch (position){
            case 0 :
                Call<MovieResult> popularCall = tmdbAPi.loadPopular(BuildConfig.API) ;
                popularCall.enqueue(this);
                break;
            case 1 :

                Call<MovieResult> ratingCall = tmdbAPi.loadHighRated(BuildConfig.API) ;
                ratingCall.enqueue(this);
                break;
            default:
                Call<MovieResult> popularCall1 = tmdbAPi.loadPopular(BuildConfig.API) ;
                popularCall1.enqueue(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private boolean checkConnectivity(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected ;
    }


}
