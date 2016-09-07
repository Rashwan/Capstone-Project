package com.rashwan.redditclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rashwan.redditclient.service.AuthService;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @Inject AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RedditClientApplication.getApplicationComponent().inject(this);

        authService.getAccessToken().subscribe(accessToken -> {
            Timber.d(accessToken.accessToken());
        },throwable -> Timber.d(throwable.getMessage())
        ,() -> Timber.d("completed"));
    }
}
