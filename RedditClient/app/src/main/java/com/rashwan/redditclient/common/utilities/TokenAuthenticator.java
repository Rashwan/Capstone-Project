package com.rashwan.redditclient.common.utilities;

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
    @Inject AuthService authService;

    public TokenAuthenticator() {
        RedditClientApplication.getApplicationComponent().inject(this);
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        System.out.println("Authenticating for response: " + response);
        System.out.println("Challenges: " + response.challenges());
        String accessToken = authService.getAccessToken().toBlocking().first().accessToken();

        return response.request().newBuilder()
                .header("Authorization","bearer " + accessToken)
                .build();
    }
}
