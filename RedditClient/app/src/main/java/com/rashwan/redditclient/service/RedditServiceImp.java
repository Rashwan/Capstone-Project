package com.rashwan.redditclient.service;

import com.rashwan.redditclient.data.RedditApi;
import com.rashwan.redditclient.data.model.ListingResponse;
import com.rashwan.redditclient.data.model.SubredditDetailsResponse;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public class RedditServiceImp implements RedditService{
    private final Retrofit retrofit;


    public RedditServiceImp(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Observable<ListingResponse> getHotPosts() {
        return retrofit.create(RedditApi.class).getHotPosts();
    }

    @Override
    public Observable<SubredditDetailsResponse> getSubredditDetails(String subreddit) {
        return retrofit.create(RedditApi.class).getSubredditDetails(subreddit);
    }

    @Override
    public Observable<ListingResponse> getSubredditPosts(String subreddit) {
        return retrofit.create(RedditApi.class).getSubredditPosts(subreddit);
    }
}
