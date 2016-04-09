package me.seatech.movienama.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.seatech.movienama.R;
import me.seatech.movienama.adapter.ReviewListAdapter;
import me.seatech.movienama.schemas.Comment;
import me.seatech.movienama.schemas.CommentResult;
import me.seatech.movienama.util.Api;
import me.seatech.movienama.util.TmdbAPi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * Created by yesalam on 4/9/16.
 */
public class Review extends Fragment implements Callback<CommentResult> {

    @Bind(R.id.list_review)
    ListView commentslv ;
    Retrofit retrofit ;
    TmdbAPi tmdbAPi ;
    List<Comment> comments ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbAPi = retrofit.create(TmdbAPi.class) ;



    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        Call<CommentResult> call = tmdbAPi.fetchComment(bundle.getInt("id"),Api.API_KEY) ;
        call.enqueue(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_review,container,false) ;
        ButterKnife.bind(this,rootView) ;
        return rootView;
    }

    @Override
    public void onResponse(Call<CommentResult> call, Response<CommentResult> response) {
        comments = response.body().getComments() ;
        loadComments();
    }

    @Override
    public void onFailure(Call<CommentResult> call, Throwable t) {

    }

    private void loadComments(){
        ReviewListAdapter adapter = new ReviewListAdapter(getContext(),comments);
        commentslv.setAdapter(adapter);
    }
}
