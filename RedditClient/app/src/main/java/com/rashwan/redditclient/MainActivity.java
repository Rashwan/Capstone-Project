package com.rashwan.redditclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rashwan.redditclient.data.model.RedditPost;
import com.rashwan.redditclient.service.AuthService;
import com.rashwan.redditclient.service.RedditService;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @Inject AuthService authService;
    @Inject RedditService redditService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RedditClientApplication.getApplicationComponent().inject(this);


        redditService.getHotPosts().subscribe(listingResponse -> {
            List<RedditPost> posts = listingResponse.getData().getPosts();
            Timber.d(posts.get(0).getRedditPostData().title());
        }, Timber::d,() -> Timber.d("Completed hot"));
    }
}
