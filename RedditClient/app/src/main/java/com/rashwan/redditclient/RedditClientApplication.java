package com.rashwan.redditclient;

import android.app.Application;

import com.rashwan.redditclient.DI.ApplicationComponent;
import com.rashwan.redditclient.DI.ApplicationModule;
import com.rashwan.redditclient.DI.DaggerApplicationComponent;
import com.rashwan.redditclient.feature.browseFrontPage.injection.BrowseFrontPageComponent;
import com.rashwan.redditclient.feature.browseFrontPage.injection.BrowseFrontPageModule;

import timber.log.Timber;

/**
 * Created by rashwan on 9/7/16.
 */

public class RedditClientApplication extends Application {
    public static ApplicationComponent applicationComponent;
    private BrowseFrontPageComponent browseFrontPageComponent;

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

    public void ReleaseBrowseFrontPageComponent(){
        browseFrontPageComponent = null;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
