package com.rashwan.redditclient.ui.feature.subredditDetails.injection;

import com.rashwan.redditclient.DI.PerActivity;
import com.rashwan.redditclient.ui.feature.subredditDetails.SubredditDetailsActivity;

import dagger.Subcomponent;

/**
 * Created by rashwan on 9/10/16.
 */

@PerActivity
@Subcomponent(modules = SubredditDetailsModule.class)
public interface SubredditDetailsComponent {
    void inject(SubredditDetailsActivity target);
}
