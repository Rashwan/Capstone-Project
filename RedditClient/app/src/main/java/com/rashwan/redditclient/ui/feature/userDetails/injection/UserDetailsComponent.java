package com.rashwan.redditclient.ui.feature.userDetails.injection;

import com.rashwan.redditclient.DI.PerActivity;
import com.rashwan.redditclient.ui.feature.userDetails.UserDetailsActivity;

import dagger.Subcomponent;

/**
 * Created by rashwan on 9/11/16.
 */
@PerActivity
@Subcomponent(modules = UserDetailsModule.class)
public interface UserDetailsComponent {
    void inject(UserDetailsActivity target);
}
