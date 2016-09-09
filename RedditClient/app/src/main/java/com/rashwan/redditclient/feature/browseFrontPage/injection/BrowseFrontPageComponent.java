package com.rashwan.redditclient.feature.browseFrontPage.injection;

import com.rashwan.redditclient.DI.PerActivity;
import com.rashwan.redditclient.feature.browseFrontPage.BrowseFrontPageActivity;

import dagger.Subcomponent;

/**
 * Created by rashwan on 9/9/16.
 */
@PerActivity
@Subcomponent(modules = BrowseFrontPageModule.class)
public interface BrowseFrontPageComponent {
    void inject(BrowseFrontPageActivity target);
}
