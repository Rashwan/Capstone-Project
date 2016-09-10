package com.rashwan.redditclient.data;

import com.rashwan.redditclient.data.model.ListingResponse;
import com.rashwan.redditclient.data.model.SubredditDetailsResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public interface RedditApi {
    String USER_AGENT = "User-Agent: android:com.rashwan.redditclient:v1.0.0 (by /u/ARashwan)";
    String STUB_ACCESS_TOKEN = "Authorization: bearer jA9uZeWshMwgiLipZWM1GAjmCIQ";

    @GET("hot")
    @Headers({USER_AGENT,STUB_ACCESS_TOKEN})
    Observable<ListingResponse> getHotPosts();

    @GET("r/{subreddit}/about")
    @Headers({USER_AGENT,STUB_ACCESS_TOKEN})
    Observable<SubredditDetailsResponse> getSubredditDetails(@Path("subreddit") String subreddit);

    @GET("r/{subreddit}")
    @Headers({USER_AGENT,STUB_ACCESS_TOKEN})
    Observable<ListingResponse> getSubredditPosts(@Path("subreddit") String subreddit);
}
