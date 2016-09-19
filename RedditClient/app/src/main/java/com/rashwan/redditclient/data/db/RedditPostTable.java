package com.rashwan.redditclient.data.db;

import android.support.annotation.NonNull;

/**
 * Created by rashwan on 9/18/16.
 */

public class RedditPostTable {
    @NonNull
    public static final String TABLE = "posts";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_TITLE =  "title";
    public static final String COLUMN_SCORE =  "score";
    public static final String COLUMN_SUBREDDIT = "subreddit";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    public static final String COLUMN_NUM_OF_COMMENTS = "numberOfComments";

    public static final String COLUMN_ID_WITH_TABLE_PREFIX = TABLE + "." + COLUMN_ID;
    public static final String COLUMN_AUTHOR_WITH_TABLE_PREFIX = TABLE + "." + COLUMN_AUTHOR;
    public static final String COLUMN_TITLE_WITH_TABLE_PREFIX = TABLE + "." + COLUMN_TITLE;
    public static final String COLUMN_SCORE_WITH_TABLE_PREFIX = TABLE + "." + COLUMN_SCORE;
    public static final String COLUMN_SUBREDDIT_WITH_TABLE_PREFIX = TABLE + "." + COLUMN_SUBREDDIT;
    public static final String COLUMN_THUMBNAIL_WITH_TABLE_PREFIX = TABLE + "." + COLUMN_THUMBNAIL;
    public static final String COLUMN_NUM_OF_COMMENTS_WITH_TABLE_PREFIX = TABLE + "." + COLUMN_NUM_OF_COMMENTS;

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
                + COLUMN_AUTHOR + " TEXT NOT NULL ,"
                + COLUMN_TITLE  + " TEXT NOT NULL ,"
                + COLUMN_SCORE  + " TEXT ,"
                + COLUMN_SUBREDDIT + " TEXT NOT NULL ,"
                + COLUMN_THUMBNAIL + " TEXT NOT NULL ,"
                + COLUMN_NUM_OF_COMMENTS + " INTEGER"
                + ");";
    }
}
