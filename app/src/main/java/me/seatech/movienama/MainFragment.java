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

import butterknife.Bind;
import butterknife.ButterKnife;
import me.seatech.movienama.adapter.GridAdapter;
import me.seatech.movienama.schemas.Movie;

/**
 * Created by yesalam on 3/26/16.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.gridView)
    GridView mGridView ;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar ;
    private GridAdapter mGridAdapter ;
    private List<Movie> movies ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false) ;
        ButterKnife.bind(this,rootView);
        mGridView.setOnItemClickListener(this);
        mGridView.setVisibility(View.GONE);

        return rootView ;
    }




    public void refresh(List<Movie> movies){
        this.movies = movies ;
        mProgressBar.setVisibility(View.GONE);
        mGridAdapter = new GridAdapter(getActivity(), movies) ;
        mGridView.setAdapter(mGridAdapter);
        mGridView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(),DetailActivity.class) ;
        intent.putExtra("movie",movies.get(position));
        startActivity(intent);
    }
}
