package com.rashwan.redditclient.DI;

import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.TokenAuthenticator;
import com.rashwan.redditclient.feature.browseFrontPage.injection.BrowseFrontPageComponent;
import com.rashwan.redditclient.feature.browseFrontPage.injection.BrowseFrontPageModule;
import com.rashwan.redditclient.feature.subredditDetails.injection.SubredditDetailsComponent;
import com.rashwan.redditclient.feature.subredditDetails.injection.SubredditDetailsModule;

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

    BrowseFrontPageComponent plus(BrowseFrontPageModule browseFrontPageModule);
    SubredditDetailsComponent plus(SubredditDetailsModule subredditDetailsModule);

}
