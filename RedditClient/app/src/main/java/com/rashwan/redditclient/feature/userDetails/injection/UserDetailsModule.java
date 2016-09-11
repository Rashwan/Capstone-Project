package com.rashwan.redditclient.feature.userDetails.injection;

import com.rashwan.redditclient.data.RedditApi;
import com.rashwan.redditclient.feature.browseFrontPage.BrowseFrontPageAdapter;
import com.rashwan.redditclient.feature.userDetails.UserDetailsPresenter;
import com.rashwan.redditclient.service.RedditService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 9/11/16.
 */
@Module
public class UserDetailsModule {
    @Provides
    UserDetailsPresenter provideUSerUserDetailsPresenter(RedditService redditService){
        return new UserDetailsPresenter(redditService);
    }
    @Provides
    BrowseFrontPageAdapter provideBrowseFrontPageAdapter(){
        return new BrowseFrontPageAdapter();
    }

}
