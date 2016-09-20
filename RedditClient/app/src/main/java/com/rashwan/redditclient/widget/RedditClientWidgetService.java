package com.rashwan.redditclient.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;
import com.rashwan.redditclient.R;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.provider.RedditPostMeta;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

/**
 * Created by rashwan on 9/20/16.
 */

public class RedditClientWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}
class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int widgetId;
    private List<ListingKind> posts;
    StorIOContentResolver storIOContentResolver;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//        RedditClientApplication.get(context).getApplicationComponent().inject(this);
        storIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(context.getContentResolver())
                .addTypeMapping(ListingKind.class
                        , ContentResolverTypeMapping.<ListingKind>builder()
                                .putResolver(RedditPostMeta.PUT_RESOLVER)
                                .getResolver(RedditPostMeta.GET_RESOLVER)
                                .deleteResolver(RedditPostMeta.DELETE_RESOLVER)
                                .build())
                .build();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        posts = storIOContentResolver.get().listOfObjects(ListingKind.class).withQuery(Query.builder()
                .uri(RedditPostMeta.CONTENT_URI).build()).prepare().executeAsBlocking();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Timber.d(String.valueOf(posts.size()));
        return posts.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RedditPostDataModel post = (RedditPostDataModel) posts.get(position);
        Timber.d(post.thumbnail());
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.widget_tv_title,post.title());

        try {
            Bitmap bitmap = Picasso.with(context).load(post.thumbnail()).placeholder(R.drawable.ic_reddit_logo_and_wordmark).get();
            rv.setImageViewBitmap(R.id.widget_iv_thumbnail,bitmap);
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
        return false;
    }
}
