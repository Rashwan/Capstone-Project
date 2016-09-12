package com.rashwan.redditclient.data;

import com.rashwan.redditclient.data.model.ListingResponse;
import com.rashwan.redditclient.data.model.SubredditDetailsResponse;
import com.rashwan.redditclient.data.model.UserDetailsResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public interface RedditApi {
    String USER_AGENT = "User-Agent: android:com.rashwan.redditclient:v1.0.0 (by /u/ARashwan)";

    @GET("hot")
    @Headers(USER_AGENT)
    Observable<ListingResponse> getHotPosts(@Header("Authorization") String accessToken);

    @GET("r/{subreddit}/about")
    @Headers(USER_AGENT)
    Observable<SubredditDetailsResponse> getSubredditDetails(
            @Header("Authorization") String accessToken
            ,@Path("subreddit") String subreddit);

    @GET("r/{subreddit}")
    @Headers(USER_AGENT)
    Observable<ListingResponse> getSubredditPosts(
            @Header("Authorization") String accessToken
            ,@Path("subreddit") String subreddit);

    @GET("user/{username}/about")
    @Headers(USER_AGENT)
    Observable<UserDetailsResponse> getUserDetails(
            @Header("Authorization") String accessToken
            ,@Path("username") String username);

    @GET("user/{username}/submitted")
    @Headers(USER_AGENT)
    Observable<ListingResponse> getUserPosts(
            @Header("Authorization") String accessToken
            ,@Path("username") String username);
}
