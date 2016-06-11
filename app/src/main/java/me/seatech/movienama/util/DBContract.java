package me.seatech.movienama.util;

import android.net.Uri;
import android.provider.BaseColumns;

import retrofit2.http.PUT;

/**
 * Created by yesalam on 5/4/16.
 */
public class DBContract {
    public static final String AUTHORITY = "me.seatech.movienama" ;
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY) ;

    public static final class DBEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies" ;
        public static final String COLUMN_MOVIE_NAME = "title" ;
        public static final String COLUMN_OVERVIEW = "overview" ;
        public static final String COLUMN_RELEASE = "release_date" ;
        public static final String COLUMN_RATING = "vote_average" ;
        public static final String COLUMN_BACKDROP_URL = "backdrop_path" ;
        public static final String COLUMN_POSTER = "poster_path" ;
        public static final String COLUMN_ID = "_ID" ;
    }
}
