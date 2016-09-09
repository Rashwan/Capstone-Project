package com.rashwan.redditclient.feature.browseFrontPage.injection;

import com.rashwan.redditclient.feature.browseFrontPage.BrowseFrontPageAdapter;
import com.rashwan.redditclient.feature.browseFrontPage.BrowseFrontPagePresenter;
import com.rashwan.redditclient.service.RedditService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 9/9/16.
 */
@Module
public class BrowseFrontPageModule {

    @Provides
    public BrowseFrontPagePresenter provideBrowseFrontPagePresenter(RedditService redditService){
        return new BrowseFrontPagePresenter(redditService);
    }

    @Provides
    public BrowseFrontPageAdapter provideBrowseFrontPageAdapter(){
        return new BrowseFrontPageAdapter();
    }

}
