package com.rashwan.redditclient;

import android.app.Application;

import com.rashwan.redditclient.DI.ApplicationComponent;
import com.rashwan.redditclient.DI.ApplicationModule;
import com.rashwan.redditclient.DI.DaggerApplicationComponent;
import com.rashwan.redditclient.ui.feature.browseFrontPage.injection.BrowseFrontPageComponent;
import com.rashwan.redditclient.ui.feature.browseFrontPage.injection.BrowseFrontPageModule;
import com.rashwan.redditclient.ui.feature.postDetails.injection.PostDetailsComponent;
import com.rashwan.redditclient.ui.feature.postDetails.injection.PostDetailsModule;
import com.rashwan.redditclient.ui.feature.subredditDetails.injection.SubredditDetailsComponent;
import com.rashwan.redditclient.ui.feature.subredditDetails.injection.SubredditDetailsModule;
import com.rashwan.redditclient.ui.feature.userDetails.injection.UserDetailsComponent;
import com.rashwan.redditclient.ui.feature.userDetails.injection.UserDetailsModule;

import timber.log.Timber;

/**
 * Created by rashwan on 9/7/16.
 */

public class RedditClientApplication extends Application {
    public static ApplicationComponent applicationComponent;
    private BrowseFrontPageComponent browseFrontPageComponent;
    private SubredditDetailsComponent subredditDetailsComponent;
    private UserDetailsComponent userDetailsComponent;
    private PostDetailsComponent postDetailsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = createAppComponent();

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });
    }

    private ApplicationComponent createAppComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .build();
    }

    public BrowseFrontPageComponent createBrowseFrontPageComponent(){
        browseFrontPageComponent = applicationComponent.plus(new BrowseFrontPageModule());
        return browseFrontPageComponent;
    }
    public SubredditDetailsComponent createSubredditDetailsComponent(){
        subredditDetailsComponent = applicationComponent.plus(new SubredditDetailsModule());
        return subredditDetailsComponent;
    }
    public UserDetailsComponent createUserDetailsComponent(){
        userDetailsComponent = applicationComponent.plus(new UserDetailsModule());
        return userDetailsComponent;
    }

    public PostDetailsComponent createPostDetailsComponent(){
        postDetailsComponent = applicationComponent.plus(new PostDetailsModule());
        return postDetailsComponent;
    }

    public void releaseBrowseFrontPageComponent(){
        browseFrontPageComponent = null;
    }
    public void releaseSubredditDetailsComponent(){
        subredditDetailsComponent = null;
    }
    public void releaseUserDetailsComponent(){
        userDetailsComponent = null;
    }
    public void releasePostDetailsComponent(){
        postDetailsComponent = null;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
