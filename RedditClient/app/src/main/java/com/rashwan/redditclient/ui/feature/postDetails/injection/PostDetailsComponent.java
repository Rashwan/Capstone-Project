package com.rashwan.redditclient.ui.feature.postDetails.injection;

import com.rashwan.redditclient.DI.PerActivity;
import com.rashwan.redditclient.ui.feature.postDetails.PostDetailsActivity;

import dagger.Subcomponent;

/**
 * Created by rashwan on 9/15/16.
 */
@PerActivity
@Subcomponent(modules = PostDetailsModule.class)
public interface PostDetailsComponent {
    void inject(PostDetailsActivity target);
}
