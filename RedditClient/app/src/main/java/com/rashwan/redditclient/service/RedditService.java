package com.rashwan.redditclient.service;

import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.ListingResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public interface RedditService {
    Observable<ListingResponse> getPopularSubreddits();
    Observable<ListingResponse> searchPosts(String query);
    Observable<ListingKind> getSubredditDetails(String subreddit);
    Observable<ListingResponse> getSubredditPosts(String subreddit,String after,int count);
    Observable<ListingKind> getUserDetails(String username);
    Observable<ListingResponse> getUserPosts(String username,String after,int count);
    Observable<List<ListingResponse>> getPostDetails(String subreddit,String postId);
}
