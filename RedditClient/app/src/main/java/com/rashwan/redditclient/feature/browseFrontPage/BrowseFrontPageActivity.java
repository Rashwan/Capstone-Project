package com.rashwan.redditclient.feature.browseFrontPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.data.model.RedditPost;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseFrontPageActivity extends AppCompatActivity implements BrowseFrontPageView {
    @Inject BrowseFrontPagePresenter presenter;
    @Inject BrowseFrontPageAdapter adapter;

    @BindView(R.id.rv_browse_front_page) RecyclerView rvBrowseFrontPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_front_page);
        ((RedditClientApplication)getApplication()).createBrowseFrontPageComponent().inject(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvBrowseFrontPage.setHasFixedSize(true);
        rvBrowseFrontPage.addItemDecoration(itemDecoration);
        rvBrowseFrontPage.setLayoutManager(linearLayoutManager);
        rvBrowseFrontPage.setAdapter(adapter);
        presenter.getPosts();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void showPosts(List<RedditPost> posts) {
        adapter.addPosts(posts);
        adapter.notifyDataSetChanged();
    }
}
