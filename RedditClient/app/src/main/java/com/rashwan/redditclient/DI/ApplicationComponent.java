package com.rashwan.redditclient.DI;

import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.TokenAuthenticator;
import com.rashwan.redditclient.data.provider.RedditPostContentProvider;
import com.rashwan.redditclient.ui.feature.browseFrontPage.injection.BrowseFrontPageComponent;
import com.rashwan.redditclient.ui.feature.browseFrontPage.injection.BrowseFrontPageModule;
import com.rashwan.redditclient.ui.feature.postDetails.injection.PostDetailsComponent;
import com.rashwan.redditclient.ui.feature.postDetails.injection.PostDetailsModule;
import com.rashwan.redditclient.ui.feature.subredditDetails.injection.SubredditDetailsComponent;
import com.rashwan.redditclient.ui.feature.subredditDetails.injection.SubredditDetailsModule;
import com.rashwan.redditclient.ui.feature.userDetails.injection.UserDetailsComponent;
import com.rashwan.redditclient.ui.feature.userDetails.injection.UserDetailsModule;
import com.rashwan.redditclient.widget.WidgetRemoteViewsFactory;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rashwan on 9/7/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(RedditClientApplication target);
    void inject(TokenAuthenticator target);
    void inject(RedditPostContentProvider target);
    void inject(WidgetRemoteViewsFactory target);

    BrowseFrontPageComponent plus(BrowseFrontPageModule browseFrontPageModule);
    SubredditDetailsComponent plus(SubredditDetailsModule subredditDetailsModule);
    UserDetailsComponent plus(UserDetailsModule userDetailsModule);
    PostDetailsComponent plus(PostDetailsModule postDetailsModule);

}
