package com.rashwan.redditclient.feature.subredditDetails.injection;

import com.rashwan.redditclient.feature.common.BrowsePostsAdapter;
import com.rashwan.redditclient.feature.subredditDetails.SubredditDetailsPresenter;
import com.rashwan.redditclient.service.RedditService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 9/10/16.
 */

@Module
public class SubredditDetailsModule {
    @Provides
    SubredditDetailsPresenter provideSubredditDetailsPresenter(RedditService redditService){
        return new SubredditDetailsPresenter(redditService);
    }
    @Provides
    BrowsePostsAdapter provideBrowseFrontPageAdapter(){
        return new BrowsePostsAdapter();
    }
}
