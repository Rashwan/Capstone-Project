package com.rashwan.redditclient.service;

import com.rashwan.redditclient.data.model.ListingResponse;

import rx.Observable;

/**
 * Created by rashwan on 9/8/16.
 */

public interface RedditService {
    Observable<ListingResponse> getHotPosts();
}
