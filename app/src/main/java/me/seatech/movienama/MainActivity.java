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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import me.seatech.movienama.scheme.*;
import me.seatech.movienama.scheme.Result;
import me.seatech.movienama.util.Api;
import me.seatech.movienama.util.TmdbAPi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<Movies>,AdapterView.OnItemSelectedListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName() ;
    private Toolbar toolbar ;
    private Spinner spinner ;
    private Retrofit retrofit ;
    private TmdbAPi tmdbAPi ;
    private MainFragment mainFragment ;
    private ArrayList<Result> results ;
    private int choice=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
        mainFragment= (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        setSupportActionBar(toolbar);
        setSpinner();



        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbAPi = retrofit.create(TmdbAPi.class) ;

        if(savedInstanceState != null ){
            spinner.setSelection(savedInstanceState.getInt("choice"));
            results = savedInstanceState.getParcelableArrayList("key");
            mainFragment.refresh(results);
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
        outState.putParcelableArrayList("key",results);
        outState.putInt("choice",choice);
    }

    @Override
    public void onResponse(Call<Movies> call, Response<Movies> response) {
        results = (ArrayList<Result>) response.body().getResults();
        mainFragment.refresh(results);
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
        choice = position ;
        switch (position){
            case 0 :
                Call<Movies> popularCall = tmdbAPi.loadPopular(Api.API_KEY) ;
                popularCall.enqueue(this);
                break;
            case 1 :
                Call<Movies> ratingCall = tmdbAPi.loadHighRated(Api.API_KEY) ;
                ratingCall.enqueue(this);
                break;
            default:
                Call<Movies> popularCall1 = tmdbAPi.loadPopular(Api.API_KEY) ;
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
