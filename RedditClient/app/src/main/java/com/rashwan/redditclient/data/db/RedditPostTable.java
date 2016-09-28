package com.rashwan.redditclient.data.db;

import android.support.annotation.NonNull;

/**
 * Created by rashwan on 9/18/16.
 */

public class RedditPostTable {
    @NonNull
    public static final String TABLE = "posts";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_POST_ID = "postId";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_TITLE =  "title";
    public static final String COLUMN_SCORE =  "score";
    public static final String COLUMN_SUBREDDIT = "subreddit";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    public static final String COLUMN_NUM_OF_COMMENTS = "numberOfComments";
    public static final String COLUMN_IS_SELF = "isSelf";


    // This is just class with Meta Data, we don't need instances
    private RedditPostTable() {
        throw new IllegalStateException("No instances please");
    }

    // Better than static final field -> allows VM to unload useless String
    // Because you need this string only once per application life on the device
    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"
                + COLUMN_POST_ID + " TEXT NOT NULL ,"
                + COLUMN_AUTHOR + " TEXT NOT NULL ,"
                + COLUMN_TITLE  + " TEXT NOT NULL ,"
                + COLUMN_SCORE  + " TEXT ,"
                + COLUMN_SUBREDDIT + " TEXT NOT NULL ,"
                + COLUMN_THUMBNAIL + " TEXT ,"
                + COLUMN_NUM_OF_COMMENTS + " INTEGER ,"
                + COLUMN_IS_SELF + " BOOLEAN"
                + ");";
    }
}
