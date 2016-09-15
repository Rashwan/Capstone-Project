package com.rashwan.redditclient.service;

import android.content.SharedPreferences;

import com.rashwan.redditclient.data.RedditApi;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.ListingResponse;

import java.util.List;

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
    public Observable<ListingResponse> getPopularSubreddits() {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getPopularSubreddits(accessToken);
    }

    @Override
    public Observable<ListingResponse> searchPosts(String query) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        String modifiedQuery = "title:" + query;
        return retrofit.create(RedditApi.class).searchPosts(accessToken,modifiedQuery);
    }

    @Override
    public Observable<ListingKind> getSubredditDetails(String subreddit) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getSubredditDetails(accessToken,subreddit);
    }

    @Override
    public Observable<ListingResponse> getSubredditPosts(String subreddit) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getSubredditPosts(accessToken,subreddit);
    }

    @Override
    public Observable<ListingKind> getUserDetails(String username) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getUserDetails(accessToken,username);
    }

    @Override
    public Observable<ListingResponse> getUserPosts(String username) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getUserPosts(accessToken,username);
    }

    @Override
    public Observable<List<ListingResponse>> getPostDetails(String subreddit, String postId) {
        accessToken = sp.getString(KEY_ACCESS_TOKEN,STUB_ACCESS_TOKEN);
        return retrofit.create(RedditApi.class).getPostDetails(accessToken,subreddit,postId);
    }
}
