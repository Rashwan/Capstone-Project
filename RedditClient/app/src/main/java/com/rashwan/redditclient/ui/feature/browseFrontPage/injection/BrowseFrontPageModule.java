package com.rashwan.redditclient.ui.feature.browseFrontPage.injection;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.rashwan.redditclient.service.RedditService;
import com.rashwan.redditclient.ui.common.BrowsePostsAdapter;
import com.rashwan.redditclient.ui.feature.browseFrontPage.BrowseFrontPagePresenter;

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
    public BrowsePostsAdapter provideBrowsePostsAdapter(FirebaseAnalytics firebaseAnalytics){
        return new BrowsePostsAdapter(firebaseAnalytics);
    }

}
