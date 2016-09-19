package com.rashwan.redditclient.data.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.contentresolver.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.contentresolver.operations.delete.DeleteResolver;
import com.pushtorefresh.storio.contentresolver.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio.contentresolver.operations.get.GetResolver;
import com.pushtorefresh.storio.contentresolver.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.contentresolver.operations.put.PutResolver;
import com.pushtorefresh.storio.contentresolver.queries.DeleteQuery;
import com.pushtorefresh.storio.contentresolver.queries.InsertQuery;
import com.pushtorefresh.storio.contentresolver.queries.UpdateQuery;
import com.rashwan.redditclient.data.db.RedditPostTable;
import com.rashwan.redditclient.data.model.RedditPostDataModel;

import timber.log.Timber;

/**
 * Created by rashwan on 9/19/16.
 */

public class RedditPostMeta {
    /**
     * The Content Authority is a name for the entire content provider, similar to the relationship
     * between a domain name and its website. A convenient string to use for content authority is
     * the package name for the app, since it is guaranteed to be unique on the device.
     */
    public static final String CONTENT_AUTHORITY = "com.rashwan.redditclient";

    /**
     * The content authority is used to create the base of all URIs which apps will use to
     * contact this content provider.
     */
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_POST = "post";

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POST).build();

    @NonNull
    public static final PutResolver<RedditPostDataModel> PUT_RESOLVER = new DefaultPutResolver<RedditPostDataModel>() {
        @NonNull
        @Override
        protected InsertQuery mapToInsertQuery(@NonNull RedditPostDataModel object) {
            return InsertQuery.builder()
                    .uri(CONTENT_URI)
                    .build();
        }

        @NonNull
        @Override
        protected UpdateQuery mapToUpdateQuery(@NonNull RedditPostDataModel object) {
            return UpdateQuery.builder()
                    .uri(CONTENT_URI)
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
    };

    @NonNull
    public static final GetResolver<RedditPostDataModel> GET_RESOLVER = new DefaultGetResolver<RedditPostDataModel>() {
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
    };

    @NonNull
    public static final DeleteResolver<RedditPostDataModel> DELETE_RESOLVER = new DefaultDeleteResolver<RedditPostDataModel>() {
        @NonNull
        @Override
        protected DeleteQuery mapToDeleteQuery(@NonNull RedditPostDataModel object) {
            return DeleteQuery.builder()
                    .uri(CONTENT_URI)
                    .where(RedditPostTable.COLUMN_ID + " = ?")
                    .whereArgs(object.getId())
                    .build();
        }
    };
}
