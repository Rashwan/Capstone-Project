package com.rashwan.redditclient.ui.feature.userDetails.injection;

import com.rashwan.redditclient.service.RedditService;
import com.rashwan.redditclient.ui.feature.userDetails.UserDetailsAdapter;
import com.rashwan.redditclient.ui.feature.userDetails.UserDetailsPresenter;

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
    UserDetailsAdapter provideUserDetailsAdapter(){
        return new UserDetailsAdapter();
    }

}
