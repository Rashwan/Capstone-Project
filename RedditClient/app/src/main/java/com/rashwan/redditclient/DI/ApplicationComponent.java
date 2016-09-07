package com.rashwan.redditclient.DI;

import com.rashwan.redditclient.MainActivity;
import com.rashwan.redditclient.RedditClientApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rashwan on 9/7/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(RedditClientApplication target);
    void inject(MainActivity target);

}
