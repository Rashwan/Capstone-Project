package com.rashwan.redditclient;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.rashwan.redditclient.DI.ApplicationComponent;
import com.rashwan.redditclient.DI.ApplicationModule;
import com.rashwan.redditclient.DI.DaggerApplicationComponent;
import com.rashwan.redditclient.data.provider.RedditPostMeta;
import com.rashwan.redditclient.data.db.RedditPostTable;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.feature.browseFrontPage.injection.BrowseFrontPageComponent;
import com.rashwan.redditclient.feature.browseFrontPage.injection.BrowseFrontPageModule;
import com.rashwan.redditclient.feature.subredditDetails.injection.SubredditDetailsComponent;
import com.rashwan.redditclient.feature.subredditDetails.injection.SubredditDetailsModule;
import com.rashwan.redditclient.feature.userDetails.injection.UserDetailsComponent;
import com.rashwan.redditclient.feature.userDetails.injection.UserDetailsModule;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by rashwan on 9/7/16.
 */

public class RedditClientApplication extends Application {
    public static ApplicationComponent applicationComponent;
    private BrowseFrontPageComponent browseFrontPageComponent;
    private SubredditDetailsComponent subredditDetailsComponent;
    private UserDetailsComponent userDetailsComponent;
    @Inject StorIOSQLite storIOSQLite;
    @Inject StorIOContentResolver storIOContentResolver;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = createAppComponent();
        applicationComponent.inject(this);

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });
        RedditPostDataModel redditPostDataModel = RedditPostDataModel.create("author","score"
        ,"subreddit","thumbnail","title",5);
        RedditPostDataModel redditPostDataModel2 = RedditPostDataModel.create("author2","score2"
        ,"subreddit2","thumbnail2","title2",5);
         storIOSQLite.put().object(redditPostDataModel)
                .prepare().executeAsBlocking();
        storIOSQLite.put().object(redditPostDataModel2)
                .prepare().executeAsBlocking();

        List<RedditPostDataModel> redditPostDataModel1 = storIOSQLite.get()
                .listOfObjects(RedditPostDataModel.class).withQuery(
                Query.builder().table(RedditPostTable.TABLE).build())
                .prepare().executeAsBlocking();
        for (RedditPostDataModel post: redditPostDataModel1) {
            Timber.d(String.valueOf(post.getId()));
        }

        RedditPostDataModel redditPostDataModel3 = RedditPostDataModel.create("author3","score3"
                ,"subreddit3","thumbnail3","title3",5);
        PutResult putResult = storIOContentResolver.put().object(redditPostDataModel3).prepare()
                .executeAsBlocking();
        Timber.d(String.valueOf(putResult.numberOfRowsUpdated()));
        List<RedditPostDataModel> redditPostDataModel4 = storIOContentResolver.get().listOfObjects(RedditPostDataModel.class).withQuery(
                com.pushtorefresh.storio.contentresolver.queries.Query.builder()
                        .uri(RedditPostMeta.CONTENT_URI)
                        .build()
        ).prepare().executeAsBlocking();

        for (RedditPostDataModel post: redditPostDataModel4) {
            Timber.d(String.valueOf(post.title()));
        }

    }
    @NonNull
    public static RedditClientApplication get(@NonNull Context context) {
        return (RedditClientApplication) context.getApplicationContext();
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

    public void releaseBrowseFrontPageComponent(){
        browseFrontPageComponent = null;
    }
    public void releaseSubredditDetailsComponent(){
        subredditDetailsComponent = null;
    }
    public void releaseUserDetailsComponent(){
        userDetailsComponent = null;
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null){
            applicationComponent = createAppComponent();
        }
        return applicationComponent;
    }

}
