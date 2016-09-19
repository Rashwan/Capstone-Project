package com.rashwan.redditclient.data.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rashwan on 9/18/16.
 */

public class RedditPostDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "moviesDatabase";
    private static final int DATABASE_VERSION = 1;

    public RedditPostDBHelper(Application context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RedditPostTable.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
