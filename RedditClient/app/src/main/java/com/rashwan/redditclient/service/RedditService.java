package com.rashwan.redditclient.service;

import com.rashwan.redditclient.data.model.ListingResponse;
import com.rashwan.redditclient.data.model.SubredditDetailsResponse;
import com.rashwan.redditclient.data.model.SubredditListingResponse;
import com.rashwan.redditclient.data.model.UserDetailsResponse;

import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public interface RedditService {
    Observable<SubredditListingResponse> getPopularSubreddits();
    Observable<ListingResponse> searchPosts(String query);
    Observable<SubredditDetailsResponse> getSubredditDetails(String subreddit);
    Observable<ListingResponse> getSubredditPosts(String subreddit);
    Observable<UserDetailsResponse> getUserDetails(String username);
    Observable<ListingResponse> getUserPosts(String username);
}
