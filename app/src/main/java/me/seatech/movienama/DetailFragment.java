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

import butterknife.Bind;
import butterknife.ButterKnife;
import me.seatech.movienama.schemas.Movie;
import me.seatech.movienama.util.Api;

/**
 * Created by yesalam on 3/26/16.
 */
public class DetailFragment extends Fragment {

    @Bind(R.id.title_tv)
    TextView titleTextView ;
    @Bind(R.id.release_tv)
    TextView releaseTextView ;
    @Bind(R.id.rating_tv)
    TextView ratingTextView ;
    @Bind(R.id.overview_tv)
    TextView overviewTextView ;
    @Bind(R.id.posterImage)
    ImageView posterImageView ;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail_info,container,false) ;

        ButterKnife.bind(this,rootView);

        Bundle intent = getArguments();
        Movie movie = intent.getParcelable("movie");
        if (movie != null) {
            String url = Api.BASE_URL_IMAGE+Api.SIZE_W342+movie.getPosterPath() ;
            Picasso.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.movie_reel_clip_art)
                    .error(R.drawable.movie_reel_clip_art)
                    .into(posterImageView);

            titleTextView.setText(movie.getTitle());
            ratingTextView.setText(movie.getVoteAverage()+"/10");
            overviewTextView.setText(movie.getOverview());

            String dateString = movie.getReleaseDate();

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
