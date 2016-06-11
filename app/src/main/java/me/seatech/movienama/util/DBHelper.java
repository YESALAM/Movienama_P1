package me.seatech.movienama.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.seatech.movienama.util.DBContract.* ;

/**
 * Created by yesalam on 5/4/16.
 */
public class DBHelper extends SQLiteOpenHelper {

   static final String DATABASE_NAME =  "movinama" ;
   static final int DATABASE_VERSION =  1 ;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_QUERY = "create table " + DBEntry.TABLE_NAME + " (" +
                DBEntry.COLUMN_ID + " integer primary key ,"+
                DBEntry.COLUMN_MOVIE_NAME+" text not null ," +
                DBEntry.COLUMN_OVERVIEW +" text not null ,"+
                DBEntry.COLUMN_RELEASE + " text not null ,"+
                DBEntry.COLUMN_RATING+ " double not null ," +
                DBEntry.COLUMN_BACKDROP_URL +" text not null ,"+
                DBEntry.COLUMN_POSTER + " text not null " +
                ")" ;
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + DBEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
