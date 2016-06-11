package me.seatech.movienama.util;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import me.seatech.movienama.util.DBContract.*;

/**
 * Created by yesalam on 5/4/16.
 */
public class DBProvider extends ContentProvider {

    DBHelper dbHelper ;
    SQLiteDatabase database ;

    static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH) ;
    static {
        URI_MATCHER.addURI(DBContract.AUTHORITY,"favourites",0);
        URI_MATCHER.addURI(DBContract.AUTHORITY,"#",1);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext()) ;
        database = dbHelper.getWritableDatabase() ;
        return  true ;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result ;
        if(sortOrder == null) sortOrder = DBEntry.COLUMN_ID ;
        switch (URI_MATCHER.match(uri)){
            case 0:
             result = database.query(DBEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 1:
                result = database.query(DBEntry.TABLE_NAME,projection,DBEntry.COLUMN_ID+" =?",new String[]{uri.getLastPathSegment()},null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Didn't get it");
        }
        result.setNotificationUri(getContext().getContentResolver(),uri);
        return result;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result ;
        long _id = database.insert(DBEntry.TABLE_NAME,null,values) ;
        if(_id>0){
            result = ContentUris.withAppendedId(DBContract.CONTENT_URI,_id);
            getContext().getContentResolver().notifyChange(result,null);
            return result ;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = database.delete(DBEntry.TABLE_NAME,DBEntry.COLUMN_ID+" = ?",selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return result ;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return 0;
    }
}
