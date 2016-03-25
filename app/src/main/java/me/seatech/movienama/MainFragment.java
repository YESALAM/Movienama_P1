package me.seatech.movienama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.List;

import me.seatech.movienama.adapter.GridAdapter;
import me.seatech.movienama.scheme.Result;

/**
 * Created by yesalam on 3/26/16.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView mGridView ;
    private ProgressBar mProgressBar ;
    private GridAdapter mGridAdapter ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment,container,false) ;

        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        mGridView.setOnItemClickListener(this);
        mGridView.setVisibility(View.GONE);

        return rootView ;
    }




    public void refresh(List<Result> results){
        mProgressBar.setVisibility(View.GONE);
        mGridAdapter = new GridAdapter(getActivity(),results) ;
        mGridView.setAdapter(mGridAdapter);
        mGridView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Result movie_clicked = (Result) mGridAdapter.getItem(position);
        Intent intent = new Intent(getContext(),DetailActivity.class) ;
        intent.putExtra(DetailActivity.TITLE, movie_clicked.getOriginalTitle());
        intent.putExtra(DetailActivity.POSTER_PATH, movie_clicked.getPosterPath());
        intent.putExtra(DetailActivity.OVERVIEW, movie_clicked.getOverview());
        intent.putExtra(DetailActivity.RATING, movie_clicked.getVoteAverage());
        intent.putExtra(DetailActivity.RELEASE_DATE, movie_clicked.getReleaseDate());
        startActivity(intent);
    }
}
