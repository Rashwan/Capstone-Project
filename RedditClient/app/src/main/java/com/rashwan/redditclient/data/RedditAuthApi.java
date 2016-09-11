package com.rashwan.redditclient.data;

import com.rashwan.redditclient.data.model.AccessTokenModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by rashwan on 9/7/16.
 */

public interface RedditAuthApi {
    @FormUrlEncoded
    @POST("access_token")
    Observable<AccessTokenModel> getAccessToken(@Field("grant_type") String grantType
            , @Field("device_id") String deviceId);
}
