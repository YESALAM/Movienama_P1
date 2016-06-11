package me.seatech.movienama;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.midi.MidiOutputPort;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.seatech.movienama.schemas.Comment;
import me.seatech.movienama.schemas.CommentResult;
import me.seatech.movienama.schemas.Movie;
import me.seatech.movienama.schemas.Trailer;
import me.seatech.movienama.schemas.TrailerResult;
import me.seatech.movienama.util.Api;
import me.seatech.movienama.util.DB;
import me.seatech.movienama.util.TmdbAPi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * Created by yesalam on 3/26/16.
 */
public class DetailFragment extends Fragment implements Callback<CommentResult>, View.OnClickListener {


    public static DetailFragment instance ;

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
    @Bind(R.id.rating)
    RatingBar ratingBar ;

    FloatingActionButton floatingActionButton ;

    Movie movie ;
    Retrofit retrofit ;
    TmdbAPi tmdbAPi ;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<Trailer> trailerList = new ArrayList<>();
    @Bind(R.id.trailer_container)
    LinearLayout trailerContainer ;
    @Bind(R.id.review_container)
    LinearLayout reviewContainer ;

    public DetailFragment getInstance(Movie movie){
        instance = new DetailFragment();
        Bundle bundle = new Bundle() ;
        bundle.putParcelable("movie" , movie);
        this.setArguments(bundle);
        if(bundle!= null) Log.e("DetailFragment","sets the arguments");
        return instance ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.favorite_button);
        floatingActionButton.setOnClickListener(this);
        DB db = new DB(getContext().getContentResolver());
        if(db.isFavorite(movie.getId())){
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.fav_heart_button));
        }else {
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.fav_heart_outline));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbAPi = retrofit.create(TmdbAPi.class) ;

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail,container,false) ;

        ButterKnife.bind(this,rootView);

        Bundle bundle = getArguments();
        movie = bundle.getParcelable("movie");
        if (movie != null) {
            updateUI();
        } else {
            movie = savedInstanceState.getParcelable("movie");
            updateUI();
        }

        reviewLayout(savedInstanceState);
        trailerLayout(savedInstanceState);

        return  rootView ;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movie",movie);
        outState.putParcelableArrayList("trailers",trailerList);
        outState.putParcelableArrayList("comments",comments);
    }



    void updateUI(){
        String url = Api.BASE_URL_IMAGE+Api.SIZE_W342+movie.getPosterPath() ;
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.movie_reel_clip_art)
                .error(R.drawable.movie_reel_clip_art)
                .into(posterImageView);

        titleTextView.setText(movie.getTitle());
        ratingTextView.setText(movie.getVoteAverage()+"/10");
        overviewTextView.setText(movie.getOverview());

        ratingBar.setRating((float) (movie.getVoteAverage()/2));

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


    void reviewLayout(Bundle savedInstanceState){
        if(savedInstanceState == null ){
            Bundle bundle = getArguments();
            Call<CommentResult> call = tmdbAPi.fetchComment(movie.getId(), BuildConfig.API) ;
            call.enqueue(this);
        } else {
            comments = savedInstanceState.getParcelableArrayList("comments") ;
            loadComments();
        }
    }

    private void loadComments(){
        if(comments.size() == 0){

            /*noreviewTV.setVisibility(View.VISIBLE);*/

        }else {
            for(Comment comment : comments){
                View view ;
                view = View.inflate(getActivity(),R.layout.review_layout,null);
                ((TextView)view.findViewById(R.id.author_review)).setText(comment.getAuthor());
                ((TextView)view.findViewById(R.id.content_review)).setText(comment.getContent());
                reviewContainer.addView(view);
                viewCollaps(view);
            }
        }
    }

    @Override
    public void onResponse(Call<CommentResult> call, Response<CommentResult> response) {
        CommentResult result = response.body() ;
        if(result == null )
            Log.e("DetailFragment" , "CommentResult was null ") ;
        else{
            Log.e("DetailFragment","onResponse of CommentResult") ;
            comments = (ArrayList<Comment>) response.body().getComments();
            loadComments();
        }
    }

    @Override
    public void onFailure(Call<CommentResult> call, Throwable t) {

    }



    void trailerLayout(Bundle savedInstanceState){
        if(savedInstanceState != null ){
            trailerList = savedInstanceState.getParcelableArrayList("trailers");
            loadTrailer();
        }else{
            Bundle bundle = getArguments();
            Call<TrailerResult> call = tmdbAPi.fetchTrailer(movie.getId(), BuildConfig.API) ;
            call.enqueue(new Callback<TrailerResult>() {
                @Override
                public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                    TrailerResult result = response.body() ;
                    if(result == null ) Log.e("DetailFragment " ," TrailerResult was null ");
                    else{
                        Log.e("DetailFragment","onResponse of TrailerResult") ;
                        trailerList = (ArrayList<me.seatech.movienama.schemas.Trailer>) response.body().getTrailers();
                        loadTrailer();
                    }
                }

                @Override
                public void onFailure(Call<TrailerResult> call, Throwable t) {

                }
            });
    }}

    public void loadTrailer(){
        for (final Trailer trailer : trailerList ) {
            View view ;
            view = View.inflate(getActivity(),R.layout.trailer_layout,null) ;
            ((TextView)view.findViewById(R.id.trailer_tv)).setText(trailer.getName());
            ImageView trailerImage = (ImageView) view.findViewById(R.id.trailer_iv) ;
            Picasso.with(getActivity()).load("http://img.youtube.com/vi/" + trailer.getKey() + "/hqdefault.jpg").into(trailerImage);


            trailerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playTrailer(trailer.getKey());
                }
            });
            ((CardView) view.findViewById(R.id.trailer_card)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playTrailer(trailer.getKey());
                }
            });

            trailerContainer.addView(view);
        }

    }


    public void playTrailer(String id){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            getActivity().startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Api.YOU_TUBE_URL+id));
            getActivity().startActivity(intent);
        }
    }

    public void viewCollaps(View view){
        final TextView content_review = (TextView) view.findViewById(R.id.content_review);
        final TextView collapsed_tv = (TextView)view.findViewById(R.id.collapsed_tv) ;
        content_review.post(new Runnable() {
            @Override
            public void run() {
                 if(content_review.getLineCount()<=5) {
                     collapsed_tv.setVisibility(View.GONE);
                 } else {
                     content_review.setMaxLines(5);
                     collapsed_tv.setText("More");
                     content_review.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             if(collapsed_tv.getText().equals("More")){
                                 content_review.setMaxLines(786);
                                 collapsed_tv.setText("Less");
                             } else {
                                 content_review.setMaxLines(5);
                                 collapsed_tv.setText("More");
                             }
                         }
                     });
                 }
            }
        });
    }

    @Override
    public void onClick(View v) {

        Log.e("DetailFragment","FAB clicked");
        ContentResolver contentResolver = getContext().getContentResolver() ;
        DB db = new DB(contentResolver) ;
        String toast_message ;
        if(db.isFavorite(movie.getId())){
            toast_message = "Removed from Favorites" ;
            db.removeMovie(movie.getId());
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.fav_heart_outline));
        }else{
            db.addFavoriteMovie(movie);
            toast_message = "Added to Favorites" ;
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.fav_heart_button));
        }
        Toast.makeText(getContext(), toast_message, Toast.LENGTH_SHORT).show();
    }
}
