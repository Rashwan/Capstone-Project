package com.rashwan.redditclient;

import android.app.Application;

import com.rashwan.redditclient.DI.ApplicationComponent;
import com.rashwan.redditclient.DI.ApplicationModule;
import com.rashwan.redditclient.DI.DaggerApplicationComponent;

import timber.log.Timber;

/**
 * Created by rashwan on 9/7/16.
 */

public class RedditClientApplication extends Application {
    public static ApplicationComponent applicationComponent;

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

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
