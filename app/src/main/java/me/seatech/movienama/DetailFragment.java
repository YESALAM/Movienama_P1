package me.seatech.movienama;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.seatech.movienama.util.Api;
import static me.seatech.movienama.DetailActivity.* ;

/**
 * Created by yesalam on 3/26/16.
 */
public class DetailFragment extends Fragment {

    private TextView titleTextView ;
    private TextView releaseTextView ;
    private TextView ratingTextView ;
    private TextView overviewTextView ;
    private ImageView posterImageView ;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_detail,container,false) ;

        posterImageView = (ImageView) rootView.findViewById(R.id.posterImage);
        titleTextView = (TextView) rootView.findViewById(R.id.title_tv) ;
        releaseTextView = (TextView) rootView.findViewById(R.id.release_tv) ;
        ratingTextView = (TextView) rootView.findViewById(R.id.rating_tv) ;
        overviewTextView = (TextView) rootView.findViewById(R.id.overview_tv) ;

        Bundle intent = getArguments();
        if (intent != null) {
            String url = Api.BASE_URL_IMAGE+Api.SIZE_W342+intent.getString(POSTER_PATH) ;
            Picasso.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.movie_reel_clip_art)
                    .error(R.drawable.movie_reel_clip_art)
                    .into(posterImageView);

            titleTextView.setText(intent.getString(TITLE));
            ratingTextView.setText(intent.getString(RATING)+"/10");
            overviewTextView.setText(intent.getString(OVERVIEW));

            String dateString = intent.getString(RELEASE_DATE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM", Locale.ENGLISH) ;
            try {
                Date date = dateFormat.parse(dateString) ;
                DateFormat simpleDateFormat = new SimpleDateFormat("MMM  yyyy",Locale.ENGLISH) ;
                releaseTextView.setText(simpleDateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return  rootView ;
    }
}
