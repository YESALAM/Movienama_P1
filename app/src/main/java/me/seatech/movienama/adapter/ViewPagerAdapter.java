package me.seatech.movienama.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.seatech.movienama.DetailFragment;
import me.seatech.movienama.fragments.Review;
import me.seatech.movienama.schemas.Movie;

/**
 * Created by yesalam on 3/28/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    Movie movie ;

    public ViewPagerAdapter(FragmentManager fm,Movie movie) {
        super(fm);
        this.movie = movie ;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                //info fragment
                return "info" ;
            case 1:
                //review fragment
                return "user review" ;
            case 2:
                //trailer fragment
                return "trailer" ;
            case 3:
                //more fragment
                return "more" ;
            default:
                return "love" ;

        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 3:
            case 2:
            case 0:
                DetailFragment fragment = new DetailFragment();
                Bundle bundle = new Bundle() ;
                bundle.putParcelable("movie", movie);
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                Review review = new Review() ;
                Bundle bundle1 = new Bundle() ;
                bundle1.putInt("id",movie.getId());
                review.setArguments(bundle1);
                return review ;


            default:
                return null ;

        }


    }


}
