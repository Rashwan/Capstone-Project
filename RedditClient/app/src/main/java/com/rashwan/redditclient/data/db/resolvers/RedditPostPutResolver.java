package com.rashwan.redditclient.data.db.resolvers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;
import com.rashwan.redditclient.data.db.RedditPostTable;
import com.rashwan.redditclient.data.model.RedditPostDataModel;

/**
 * Created by rashwan on 9/18/16.
 */

public class RedditPostPutResolver extends DefaultPutResolver<RedditPostDataModel> {

    @NonNull
    @Override
    protected InsertQuery mapToInsertQuery(@NonNull RedditPostDataModel object) {
        return InsertQuery.builder().table(RedditPostTable.TABLE).build();
    }

    @NonNull
    @Override
    protected UpdateQuery mapToUpdateQuery(@NonNull RedditPostDataModel object) {
        return UpdateQuery.builder().table(RedditPostTable.TABLE)
                .where(RedditPostTable.COLUMN_ID + " = ?")
                .whereArgs(object.getId())
                .build();
    }

    @NonNull
    @Override
    protected ContentValues mapToContentValues(@NonNull RedditPostDataModel object) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RedditPostTable.COLUMN_AUTHOR,object.author());
        contentValues.put(RedditPostTable.COLUMN_SCORE,object.score());
        contentValues.put(RedditPostTable.COLUMN_SUBREDDIT,object.subreddit());
        contentValues.put(RedditPostTable.COLUMN_THUMBNAIL,object.thumbnail());
        contentValues.put(RedditPostTable.COLUMN_TITLE,object.title());
        contentValues.put(RedditPostTable.COLUMN_NUM_OF_COMMENTS,object.numOfComments());
        return contentValues;
    }
}
