package com.rashwan.redditclient.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.data.db.RedditPostDBHelper;
import com.rashwan.redditclient.data.db.RedditPostTable;

import javax.inject.Inject;

/**
 * Created by rashwan on 9/19/16.
 */

public class RedditPostContentProvider extends ContentProvider{
    private static final int URI_MATCHER_CODE_POSTS = 1;
    private static final UriMatcher URI_MATCHER = new UriMatcher(1);

    static {
        URI_MATCHER.addURI(RedditPostMeta.CONTENT_AUTHORITY, RedditPostMeta.PATH_POST
                , URI_MATCHER_CODE_POSTS);
    }

    @Inject
    RedditPostDBHelper redditPostDBHelper;


    @Override
    public boolean onCreate() {
        RedditClientApplication.get(getContext()).getApplicationComponent().inject(this);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (URI_MATCHER.match(uri)){
            case URI_MATCHER_CODE_POSTS:
                return redditPostDBHelper.getReadableDatabase()
                        .query(
                                RedditPostTable.TABLE,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder
                        );
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long insertedId;
        switch (URI_MATCHER.match(uri)){
            case URI_MATCHER_CODE_POSTS:
                insertedId = redditPostDBHelper.getWritableDatabase()
                    .insert(
                        RedditPostTable.TABLE
                        ,null
                        ,values);
                break;
            default:
                return null;
        }
        if (insertedId != -1 ){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return ContentUris.withAppendedId(uri,insertedId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numberOfRowsDeleted;

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCHER_CODE_POSTS:
                numberOfRowsDeleted = redditPostDBHelper
                        .getWritableDatabase()
                        .delete(
                                RedditPostTable.TABLE,
                                selection,
                                selectionArgs
                        );
                break;

            default:
                return 0;
        }

        if (numberOfRowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int numberOfRowsAffected;

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCHER_CODE_POSTS:
                numberOfRowsAffected = redditPostDBHelper
                        .getWritableDatabase()
                        .update(
                                RedditPostTable.TABLE,
                                values,
                                selection,
                                selectionArgs
                        );
                break;

            default:
                return 0;
        }

        if (numberOfRowsAffected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsAffected;
    }
}
