package me.seatech.movienama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.seatech.movienama.R;
import me.seatech.movienama.scheme.*;
import me.seatech.movienama.util.Api;

/**
 * Created by yesalam on 2/16/16.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> movieList;

    public GridAdapter(Context context,List<Movie> movieList){
        this.context = context ;
        this.movieList = movieList;

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout layout ;
        if(convertView == null ){
             layout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.image_frame,null);

        } else {
            layout = (FrameLayout) convertView;
        }

        Movie movie = movieList.get(position) ;
        if(!movie.getTitle().isEmpty()){
            TextView textView = (TextView) layout.findViewById(R.id.posterTitle) ;
            textView.setText(movie.getTitle());
        }

        String url = Api.BASE_URL_IMAGE+Api.SIZE_W182+ movie.getPosterPath() ;
        ImageView imageView = (ImageView) layout.findViewById(R.id.posterImage);
        Picasso.with(context)
                .load(url)
                .error(R.drawable.movie_reel_clip_art)
                .placeholder(R.drawable.movie_reel_clip_art)
                .into(imageView);


        return layout;
    }
}
