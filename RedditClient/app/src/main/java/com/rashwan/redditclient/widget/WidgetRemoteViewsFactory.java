package com.rashwan.redditclient.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.operations.delete.DeleteResult;
import com.pushtorefresh.storio.contentresolver.operations.put.PutResults;
import com.pushtorefresh.storio.contentresolver.queries.DeleteQuery;
import com.pushtorefresh.storio.contentresolver.queries.Query;
import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.provider.RedditPostMeta;
import com.rashwan.redditclient.service.RedditService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by rashwan on 9/20/16.
 */
public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
//    private Context context;
    private int widgetId;
    private List<ListingKind> posts;
    @Inject StorIOContentResolver storIOContentResolver;
    @Inject RedditService redditService;
    private Context context;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        RedditClientApplication.get(context).getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        List<ListingKind> postsList = redditService
                .getSubredditPosts("All", null, 0).toBlocking().first().data().children();

        DeleteResult deleteResult = storIOContentResolver.delete().byQuery(DeleteQuery.builder()
                .uri(RedditPostMeta.CONTENT_URI).build()).prepare().executeAsBlocking();
        Timber.d("deleted %d rows",deleteResult.numberOfRowsDeleted());

        PutResults<ListingKind> putResults = storIOContentResolver.put().objects(postsList)
                .prepare().executeAsBlocking();
        Timber.d("inserted %d rows",putResults.numberOfInserts());

        posts = storIOContentResolver.get().listOfObjects(ListingKind.class).withQuery(Query.builder()
                .uri(RedditPostMeta.CONTENT_URI).build()).prepare().executeAsBlocking();
        Timber.d("Got %d rows from DB",posts.size());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RedditPostDataModel post = (RedditPostDataModel) posts.get(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.widget_tv_title,post.title());

        try {
            Bitmap bitmap = Picasso.with(context).load(post.thumbnail()).error(R.drawable.ic_reddit_logo_and_wordmark).get();
            if (bitmap != null){
                rv.setImageViewBitmap(R.id.widget_iv_thumbnail,bitmap);
            }else {
                rv.setImageViewResource(R.id.widget_iv_thumbnail,R.drawable.ic_reddit_logo_and_wordmark);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}