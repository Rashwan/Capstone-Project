package com.rashwan.redditclient.ui.feature.userDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.common.utilities.EndlessRecyclerViewScrollListener;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.UserDetailsModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class UserDetailsActivity extends AppCompatActivity implements UserDetailsView {

    @BindView(R.id.toolbar_user_details) Toolbar toolbar;
    @BindView(R.id.rv_user_posts) RecyclerView rvUserPosts;
    private static final String EXTRA_USERNAME = "com.rashwan.redditclient.ui.feature.userDetails.EXTRA_USERNAME";
    @Inject UserDetailsPresenter presenter;
    @Inject UserDetailsAdapter adapter;
    String username;


    public static Intent getUserDetailsIntent(Context context, String username) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((RedditClientApplication) getApplication()).createUserDetailsComponent().inject(this);
        presenter.attachView(this);
        Intent intent = getIntent();
        username = intent.getStringExtra(EXTRA_USERNAME);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvUserPosts.setHasFixedSize(true);
        rvUserPosts.addItemDecoration(itemDecoration);
        rvUserPosts.setLayoutManager(linearLayoutManager);
        rvUserPosts.setAdapter(adapter);
        rvUserPosts.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                Timber.d("on load more");
                presenter.getUserPosts(username);
            }
        });

        toolbar.setTitle(username);
        presenter.getUserDetails(username);
        presenter.getUserPosts(username);
    }

    @Override
    public void showUserDetails(UserDetailsModel details) {
        adapter.addUserDetails(details);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showUserPosts(List<ListingKind> posts) {
        adapter.addPosts(posts);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((RedditClientApplication)getApplication()).releaseUserDetailsComponent();
        presenter.detachView();

    }
}
