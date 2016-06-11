package me.seatech.movienama.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import me.seatech.movienama.schemas.Movie;
import me.seatech.movienama.util.DBContract.*;
/**
 * Created by yesalam on 5/4/16.
 */
public class DB {

    ContentResolver contentResolver ;

    static final String AUTHORITY_URI = "content://"+DBContract.AUTHORITY ;

    public DB(ContentResolver contentResolver) {this.contentResolver = contentResolver ;}

    public boolean isFavorite(int id){
        boolean result=false ;
        Cursor cursor = contentResolver.query(Uri.parse(AUTHORITY_URI+"/"+id),null,null,null,null,null) ;
        if(cursor != null && cursor.moveToNext()) {
            result = true;
            cursor.close();
        }
        return result;
    }

    public void addFavoriteMovie(Movie movie){
        ContentValues values = new ContentValues() ;
        values.put(DBEntry.COLUMN_ID,movie.getId());
        values.put(DBEntry.COLUMN_MOVIE_NAME,movie.getTitle());
        values.put(DBEntry.COLUMN_BACKDROP_URL,movie.getBackdropPath());
        values.put(DBEntry.COLUMN_OVERVIEW,movie.getOverview());
        values.put(DBEntry.COLUMN_POSTER,movie.getPosterPath());
        values.put(DBEntry.COLUMN_RATING,movie.getVoteAverage()+"");
        values.put(DBEntry.COLUMN_RELEASE,movie.getReleaseDate());
        contentResolver.insert(Uri.parse(AUTHORITY_URI+"/movies"),values);
    }

    public void removeMovie(int id){
        Uri uri = Uri.parse(AUTHORITY_URI+"/"+id) ;
        contentResolver.delete(uri,null,new String[]{id+""});
    }


    public ArrayList<Movie> getFavouriteMovies(){
        Uri uri = Uri.parse(AUTHORITY_URI+"/movies");
        Cursor cursor = contentResolver.query(uri,null,null,null,null,null);
        ArrayList<Movie> movies = new ArrayList<>() ;

        if(cursor!=null && cursor.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(DBEntry.COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(DBEntry.COLUMN_MOVIE_NAME)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(DBEntry.COLUMN_BACKDROP_URL)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(DBEntry.COLUMN_OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(DBEntry.COLUMN_POSTER)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(DBEntry.COLUMN_RELEASE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(DBEntry.COLUMN_RATING)));
                movies.add(movie);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return movies ;
    }

}
