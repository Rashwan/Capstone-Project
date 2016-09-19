package com.rashwan.redditclient.data.db.resolvers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;
import com.rashwan.redditclient.data.db.RedditPostTable;
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
        String subreddit = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_SUBREDDIT));
        String score = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_SCORE));
        String title = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_TITLE));
        String thumbnail = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_THUMBNAIL));
        int numOfComments = cursor.getInt(cursor.getColumnIndex(RedditPostTable.COLUMN_NUM_OF_COMMENTS));
        RedditPostDataModel post = RedditPostDataModel.create(author, score, subreddit
                , thumbnail, title, numOfComments);
        post.setId(id);
        Timber.d(post.toString());
        return post;
    }
}
