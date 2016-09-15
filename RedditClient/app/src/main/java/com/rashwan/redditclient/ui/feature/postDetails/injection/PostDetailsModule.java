package com.rashwan.redditclient.ui.feature.postDetails.injection;

import com.rashwan.redditclient.service.RedditService;
import com.rashwan.redditclient.ui.feature.postDetails.PostCommentsAdapter;
import com.rashwan.redditclient.ui.feature.postDetails.PostDetailsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rashwan on 9/15/16.
 */
@Module
public class PostDetailsModule {
    @Provides
    public PostDetailsPresenter providePostDetailsPresenter(RedditService redditService){
        return new PostDetailsPresenter(redditService);
    }

    @Provides
    public PostCommentsAdapter providePostCommentsAdapter(){
        return new PostCommentsAdapter();
    }

}
