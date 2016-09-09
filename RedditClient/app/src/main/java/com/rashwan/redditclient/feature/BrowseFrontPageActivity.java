package com.rashwan.redditclient.feature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.data.model.RedditPost;
import com.rashwan.redditclient.service.AuthService;
import com.rashwan.redditclient.service.RedditService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BrowseFrontPageActivity extends AppCompatActivity {
    @Inject
    AuthService authService;
    @Inject
    RedditService redditService;
    @BindView(R.id.rv_browse_front_page)
    RecyclerView rvBrowseFrontPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_front_page);
        ButterKnife.bind(this);
        RedditClientApplication.getApplicationComponent().inject(this);


        redditService.getHotPosts().subscribe(listingResponse -> {
            List<RedditPost> posts = listingResponse.getData().getPosts();
            Timber.d(posts.get(0).getRedditPostData().title());
        }, Timber::d, () -> Timber.d("Completed hot"));
    }
}
