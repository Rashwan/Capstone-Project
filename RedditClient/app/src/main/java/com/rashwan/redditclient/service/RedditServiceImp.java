package com.rashwan.redditclient.service;

import android.content.SharedPreferences;

import com.rashwan.redditclient.data.RedditApi;
import com.rashwan.redditclient.data.model.ListingResponse;
import com.rashwan.redditclient.data.model.SubredditDetailsResponse;
import com.rashwan.redditclient.data.model.UserDetailsResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public class RedditServiceImp implements RedditService{
    private final Retrofit retrofit;
    private final SharedPreferences sp;
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private static final String STUB_ACCESS_TOKEN = "bearer jA9uZeWshMwgiLipZWM1GAjmCIQ";
    private String accessToken;


    public RedditServiceImp(Retrofit retrofit, SharedPreferences sp) {
        this.retrofit = retrofit;
        this.sp = sp;
    }

    @Override
    public Observable<ListingResponse> getHotPosts() {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getHotPosts(accessToken);
    }

    @Override
    public Observable<SubredditDetailsResponse> getSubredditDetails(String subreddit) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getSubredditDetails(accessToken,subreddit);
    }

    @Override
    public Observable<ListingResponse> getSubredditPosts(String subreddit) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getSubredditPosts(accessToken,subreddit);
    }

    @Override
    public Observable<UserDetailsResponse> getUserDetails(String username) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getUserDetails(accessToken,username);
    }

    @Override
    public Observable<ListingResponse> getUserPosts(String username) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getUserPosts(accessToken,username);
    }
}
