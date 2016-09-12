package com.rashwan.redditclient.common.utilities;

import android.content.SharedPreferences;

import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.service.AuthService;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by rashwan on 9/8/16.
 */

public class TokenAuthenticator implements Authenticator {
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    @Inject AuthService authService;
    @Inject SharedPreferences sp;

    public TokenAuthenticator() {
        RedditClientApplication.getApplicationComponent().inject(this);
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        System.out.println("Authenticating for response: " + response);
        System.out.println("Challenges: " + response.challenges());
        String accessToken = authService.getAccessToken().toBlocking().first().accessToken();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_ACCESS_TOKEN,"bearer " + accessToken).apply();
        return response.request().newBuilder()
                .header("Authorization","bearer " + accessToken)
                .build();
    }
}
