package com.rashwan.redditclient.service;

import com.rashwan.redditclient.data.model.AccessTokenModel;

import rx.Observable;

/**
 * Created by rashwan on 9/7/16.
 */

public interface AuthService {
    Observable<AccessTokenModel> getAccessToken();

}
