package com.rashwan.redditclient.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rashwan on 9/20/16.
 */

public class RedditClientWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}

