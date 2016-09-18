package com.rashwan.redditclient.data;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.rashwan.redditclient.data.model.RedditPostDataModel;

/**
 * Created by rashwan on 9/18/16.
 */

public class RedditPostDeleteResolver extends DefaultDeleteResolver<RedditPostDataModel> {

    @NonNull
    @Override
    protected DeleteQuery mapToDeleteQuery(@NonNull RedditPostDataModel object) {
        return DeleteQuery.builder().table(RedditPostTable.TABLE)
                .where(RedditPostTable.COLUMN_ID + " = ?")
                .whereArgs(object.getId())
                .build();
    }
}
