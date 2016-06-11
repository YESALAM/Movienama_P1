package me.seatech.movienama.util;

import me.seatech.movienama.schemas.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yesalam on 2/19/16.
 */
public interface TmdbAPi {

    @GET("discover/movie?sort_by=popularity.desc")
    Call<MovieResult> loadPopular(@Query("api_key") String key);

    @GET("discover/movie?sort_by=vote_average.desc&vote_count.gte=50")
    Call<MovieResult> loadHighRated(@Query("api_key") String key);


    @GET("movie/{id}/reviews")
    Call<CommentResult> fetchComment(@Path("id") int id,@Query("api_key") String key) ;

    @GET("movie/{id}/videos")
    Call<TrailerResult> fetchTrailer(@Path("id") int id,@Query("api_key") String key) ;

}
