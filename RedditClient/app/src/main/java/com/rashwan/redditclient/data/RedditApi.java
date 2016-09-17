package com.rashwan.redditclient.data;

import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.ListingResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public interface RedditApi {
    String USER_AGENT = "User-Agent: android:com.rashwan.redditclient:v1.0.0 (by /u/ARashwan)";

    @GET("subreddits/popular")
    @Headers(USER_AGENT)
    Observable<ListingResponse> getPopularSubreddits(
            @Header("Authorization") String accessToken);

    @GET("search/")
    @Headers(USER_AGENT)
    Observable<ListingResponse> searchPosts(
            @Header("Authorization") String accessToken
            ,@Query("q") String query);

    @GET("r/{subreddit}")
    @Headers(USER_AGENT)
    Observable<ListingResponse> getSubredditPosts(
            @Header("Authorization") String accessToken
            ,@Path("subreddit") String subreddit
            ,@Query("after") String after);

    @GET("r/{subreddit}/about")
    @Headers(USER_AGENT)
    Observable<ListingKind> getSubredditDetails(
            @Header("Authorization") String accessToken
            ,@Path("subreddit") String subreddit);


    @GET("user/{username}/about")
    @Headers(USER_AGENT)
    Observable<ListingKind> getUserDetails(
            @Header("Authorization") String accessToken
            ,@Path("username") String username);

    @GET("user/{username}/submitted")
    @Headers(USER_AGENT)
    Observable<ListingResponse> getUserPosts(
            @Header("Authorization") String accessToken
            ,@Path("username") String username);

    @GET("r/{subreddit}/comments/{postId}?showmore=false?depth=2")
    @Headers(USER_AGENT)
    Observable<List<ListingResponse>> getPostDetails(
            @Header("Authorization") String accessToken
            ,@Path("subreddit") String subreddit
            ,@Path("postId") String postId);

}
