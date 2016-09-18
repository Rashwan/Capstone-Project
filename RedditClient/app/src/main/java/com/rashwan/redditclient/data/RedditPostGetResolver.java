package com.rashwan.redditclient.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;
import com.rashwan.redditclient.data.model.RedditPostDataModel;

import timber.log.Timber;

/**
 * Created by rashwan on 9/18/16.
 */

public class RedditPostGetResolver extends DefaultGetResolver<RedditPostDataModel>{
    @NonNull
    @Override
    public RedditPostDataModel mapFromCursor(@NonNull Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(RedditPostTable.COLUMN_ID));
        String author = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_AUTHOR));
        Timber.d(author);
        String subreddit = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_SUBREDDIT));
        Timber.d(subreddit);
        String score = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_SCORE));
        Timber.d(score);
        String title = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_TITLE));
        Timber.d(title);
        String thumbnail = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_THUMBNAIL));
        Timber.d(thumbnail);
        int numOfComments = cursor.getInt(cursor.getColumnIndex(RedditPostTable.COLUMN_NUM_OF_COMMENTS));
        Timber.d(String.valueOf(numOfComments));
        RedditPostDataModel post = RedditPostDataModel.create(author, score, subreddit
                , thumbnail, title, numOfComments);
        post.setId(id);
        return post;
    }
}
