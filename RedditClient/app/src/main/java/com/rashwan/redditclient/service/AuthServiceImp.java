package com.rashwan.redditclient.service;

import android.app.Application;
import android.content.SharedPreferences;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.data.RedditAuthApi;
import com.rashwan.redditclient.data.model.AccessToken;

import java.util.UUID;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rashwan on 9/7/16.
 */

public class AuthServiceImp implements AuthService{
    private final Retrofit retrofit;
    private final SharedPreferences sp;
    private final Application application;
    private static final String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";


    public AuthServiceImp(Application application,Retrofit retrofit, SharedPreferences sp) {
        this.application = application;
        this.retrofit = retrofit;
        this.sp = sp;
    }

    @Override
    public Observable<AccessToken> getAccessToken() {
        String deviceId;
        if (!sp.contains(application.getString(R.string.sp_device_id_key))){
            deviceId = createDeviceId();
        }else {
            deviceId = sp.getString(application.getString(R.string.sp_device_id_key),null);
        }
        return retrofit.create(RedditAuthApi.class)
                .getAccessToken(GRANT_TYPE,deviceId);
    }
    private String createDeviceId(){
        String deviceId =  UUID.randomUUID().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(application.getString(R.string.sp_device_id_key),deviceId).apply();
        return deviceId;
    }
}
