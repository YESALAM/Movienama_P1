package me.seatech.movienama.util;

import me.seatech.movienama.scheme.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yesalam on 2/19/16.
 */
public interface TmdbAPi {

    @GET("movie?sort_by=popularity.desc")
    Call<ResultPage> loadPopular(@Query("api_key") String key);

    @GET("movie?sort_by=vote_average.desc&vote_count.gte=50")
    Call<ResultPage> loadHighRated(@Query("api_key") String key);
}
