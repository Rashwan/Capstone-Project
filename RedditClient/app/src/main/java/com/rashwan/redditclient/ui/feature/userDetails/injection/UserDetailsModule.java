package com.rashwan.redditclient.ui.feature.userDetails.injection;

import com.rashwan.redditclient.ui.common.BrowsePostsAdapter;
import com.rashwan.redditclient.ui.feature.userDetails.UserDetailsPresenter;
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
    BrowsePostsAdapter provideBrowsePostsAdapter(){
        return new BrowsePostsAdapter();
    }

}
