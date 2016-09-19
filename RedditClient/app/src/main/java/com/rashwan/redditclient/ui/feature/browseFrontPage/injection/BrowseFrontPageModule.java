package com.rashwan.redditclient.ui.feature.browseFrontPage.injection;

import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.rashwan.redditclient.feature.common.BrowsePostsAdapter;
import com.rashwan.redditclient.feature.browseFrontPage.BrowseFrontPagePresenter;
import com.rashwan.redditclient.ui.common.BrowsePostsAdapter;
import com.rashwan.redditclient.ui.feature.browseFrontPage.BrowseFrontPagePresenter;
import com.rashwan.redditclient.service.RedditService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 9/9/16.
 */
@Module
public class BrowseFrontPageModule {

    @Provides
    public BrowseFrontPagePresenter provideBrowseFrontPagePresenter(RedditService redditService, StorIOContentResolver storIOContentResolver){
        return new BrowseFrontPagePresenter(redditService,storIOContentResolver);
    }

    @Provides
    public BrowsePostsAdapter provideBrowsePostsAdapter(){
        return new BrowsePostsAdapter();
    }

}
