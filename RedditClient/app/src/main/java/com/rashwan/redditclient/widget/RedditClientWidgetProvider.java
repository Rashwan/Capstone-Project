package com.rashwan.redditclient.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.ui.feature.browseFrontPage.BrowseFrontPageActivity;
import com.rashwan.redditclient.ui.feature.postDetails.PostDetailsActivity;

/**
 * Created by rashwan on 9/20/16.
 */

public class RedditClientWidgetProvider extends AppWidgetProvider{
    public static final String EXTRA_SUBREDDIT = "com.rashwan.redditclient.widget.EXTRA_SUBREDDIT";
    public static final String EXTRA_POST_ID = "com.rashwan.redditclient.widget.EXTRA_POST_ID";
    private static final String ACTION_POST_DETAILS = "ACTION_POST_DETAILS";
    private static final String  ACTION_BROWSE_POSTS = "ACTION_BROWSE_POSTS";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_POST_DETAILS)){
            String subreddit = intent.getStringExtra(EXTRA_SUBREDDIT);
            String postId = intent.getStringExtra(EXTRA_POST_ID);
            Intent postDetailsIntent = PostDetailsActivity
                    .getPostDetailsIntent(context, subreddit, postId);
            postDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            postDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(postDetailsIntent);
        }else if (intent.getAction().equals(ACTION_BROWSE_POSTS)){
            Intent browsePosts = new Intent(context, BrowseFrontPageActivity.class);
            browsePosts.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browsePosts.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(browsePosts);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId: appWidgetIds) {

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, RedditClientWidgetService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(R.id.widget_list_view, intent);

            Intent postDetailsIntent = new Intent(context,RedditClientWidgetProvider.class);
            postDetailsIntent.setAction(ACTION_POST_DETAILS);
            postDetailsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);
            postDetailsIntent.setData(Uri.parse(postDetailsIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent detailsPendingIntent = PendingIntent.getBroadcast(context
                    ,0,postDetailsIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            Intent browsePostsIntent = new Intent(context,RedditClientWidgetProvider.class);
            browsePostsIntent.setAction(ACTION_BROWSE_POSTS);
            browsePostsIntent.setData(Uri.parse(browsePostsIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent browsePendingIntent = PendingIntent.getBroadcast(context
                    ,1,browsePostsIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.widget_list_view,detailsPendingIntent);
            rv.setOnClickPendingIntent(R.id.widget_tv_app_name,browsePendingIntent);
            appWidgetManager.updateAppWidget(widgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
