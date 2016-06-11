package me.seatech.movienama;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    boolean isdualpane ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isdualpane = isDualPane() ;
        Log.e("MainFragment","dual pane "+isdualpane);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false) ;
        ButterKnife.bind(this,rootView);
        mGridView.setOnItemClickListener(this);
        mGridView.setVisibility(View.GONE);
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            mGridView.setNumColumns(3);
        else
            mGridView.setNumColumns(2);

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
        if(isdualpane){
            DetailFragment detailFragment = new DetailFragment();//.getInstance(movies.get(position));
            Bundle bundle = new Bundle() ;
            bundle.putParcelable("movie",movies.get(position));
            detailFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container,detailFragment).commit();
        }else {
            Intent intent = new Intent(getContext(),DetailActivity.class) ;
            intent.putExtra("movie",movies.get(position));
            startActivity(intent);
        }
    }

    public boolean isDualPane(){
        return (getActivity().findViewById(R.id.movie_detail_container) != null ) ;
    }
}
