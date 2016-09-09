package com.rashwan.redditclient.DI;

import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.TokenAuthenticator;
import com.rashwan.redditclient.feature.BrowseFrontPageActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rashwan on 9/7/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(RedditClientApplication target);
    void inject(BrowseFrontPageActivity target);
    void inject(TokenAuthenticator target);

}
