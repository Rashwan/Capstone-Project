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
import com.rashwan.redditclient.data.model.ListingKind;
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
    public static final PutResolver<ListingKind> PUT_RESOLVER = new DefaultPutResolver<ListingKind>() {
        @NonNull
        @Override
        protected InsertQuery mapToInsertQuery(@NonNull ListingKind object) {
            return InsertQuery.builder()
                    .uri(CONTENT_URI)
                    .build();
        }

        @NonNull
        @Override
        protected UpdateQuery mapToUpdateQuery(@NonNull ListingKind object) {
            if (object.getType().equals(RedditPostDataModel.class.getSimpleName())){
                RedditPostDataModel post = (RedditPostDataModel) object;
                return UpdateQuery.builder()
                        .uri(CONTENT_URI)
                        .where(RedditPostTable.COLUMN_ID + " = ?")
                        .whereArgs(post.getId())
                        .build();
            }
            return null;

        }

        @NonNull
        @Override
        protected ContentValues mapToContentValues(@NonNull ListingKind object) {
            ContentValues contentValues = new ContentValues();
            if (object.getType().equals(RedditPostDataModel.class.getSimpleName())) {
                RedditPostDataModel post = (RedditPostDataModel) object;
                Timber.d(post.thumbnail());

                contentValues.put(RedditPostTable.COLUMN_AUTHOR, post.author());
                contentValues.put(RedditPostTable.COLUMN_SCORE, post.score());
                contentValues.put(RedditPostTable.COLUMN_SUBREDDIT, post.subreddit());
                contentValues.put(RedditPostTable.COLUMN_THUMBNAIL, post.thumbnail());
                contentValues.put(RedditPostTable.COLUMN_TITLE, post.title());
                contentValues.put(RedditPostTable.COLUMN_NUM_OF_COMMENTS, post.numOfComments());
                if (!post.thumbnail().contains("http")){
                    contentValues.putNull(RedditPostTable.COLUMN_THUMBNAIL);
                }
            }

            return contentValues;
        }
    };

    @NonNull
    public static final GetResolver<ListingKind> GET_RESOLVER = new DefaultGetResolver<ListingKind>() {
        @NonNull
        @Override
        public ListingKind mapFromCursor(@NonNull Cursor cursor) {

            int id = cursor.getInt(cursor.getColumnIndex(RedditPostTable.COLUMN_ID));
            String author = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_AUTHOR));
            String subreddit = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_SUBREDDIT));
            String score = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_SCORE));
            String title = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_TITLE));
            String thumbnail = cursor.getString(cursor.getColumnIndex(RedditPostTable.COLUMN_THUMBNAIL));
            Timber.d(thumbnail);
            int numOfComments = cursor.getInt(cursor.getColumnIndex(RedditPostTable.COLUMN_NUM_OF_COMMENTS));
            RedditPostDataModel post = RedditPostDataModel.create(author, score, subreddit
                    , thumbnail, title, numOfComments);
            post.setId(id);
            Timber.d(post.toString());
            return post;
        }
    };

    @NonNull
    public static final DeleteResolver<ListingKind> DELETE_RESOLVER = new DefaultDeleteResolver<ListingKind>() {
        @NonNull
        @Override
        protected DeleteQuery mapToDeleteQuery(@NonNull ListingKind object) {
            if (object.getType().equals(RedditPostDataModel.class.getSimpleName())){
                RedditPostDataModel post = (RedditPostDataModel) object;
                return DeleteQuery.builder()
                        .uri(CONTENT_URI)
                        .where(RedditPostTable.COLUMN_ID + " = ?")
                        .whereArgs(post.getId())
                        .build();
            }
            return null;
        }
    };
}
