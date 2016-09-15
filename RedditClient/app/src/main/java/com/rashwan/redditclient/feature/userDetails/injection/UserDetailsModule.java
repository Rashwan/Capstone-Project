package com.rashwan.redditclient.feature.userDetails.injection;

import com.rashwan.redditclient.feature.common.BrowsePostsAdapter;
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
    BrowsePostsAdapter provideBrowseFrontPageAdapter(){
        return new BrowsePostsAdapter();
    }

}
