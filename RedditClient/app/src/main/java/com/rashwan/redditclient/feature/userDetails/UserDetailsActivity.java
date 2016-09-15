package com.rashwan.redditclient.feature.userDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.UserDetailsModel;
import com.rashwan.redditclient.feature.browseFrontPage.BrowseFrontPageAdapter;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsActivity extends AppCompatActivity implements UserDetailsView {

    @BindView(R.id.toolbar_user_details) Toolbar toolbar;
    @BindView(R.id.tv_username) TextView username;
    @BindView(R.id.tv_user_post_carma) TextView userPostCarma;
    @BindView(R.id.tv_user_comment_carma) TextView userCommentCarma;
    @BindView(R.id.tv_user_joined_at) TextView userJoinedAt;
    @BindView(R.id.rv_user_posts) RecyclerView rvUserPosts;
    private static final String EXTRA_USERNAME = "com.rashwan.redditclient.feature.userDetails.EXTRA_USERNAME";
    @Inject UserDetailsPresenter presenter;
    @Inject BrowseFrontPageAdapter adapter;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvUserPosts.setHasFixedSize(true);
        rvUserPosts.addItemDecoration(itemDecoration);
        rvUserPosts.setLayoutManager(linearLayoutManager);
        rvUserPosts.setAdapter(adapter);

        Intent intent = getIntent();
        String username = intent.getStringExtra(EXTRA_USERNAME);
        toolbar.setTitle(username);
        presenter.getUserDetails(username);
        presenter.getUserPosts(username);
    }

    @Override
    public void showUserDetails(UserDetailsModel details) {
        username.setText(details.getName());
        userPostCarma.setText(String.format(Locale.US,"Link Karma: %d Points",details.getLinkKarma()));
        userCommentCarma.setText(String.format(Locale.US,"Comment Karma: %d Points",details.getCommentKarma()));
        userJoinedAt.setText(details.convertUtcToLocalTime(details.getCreatedUtc()));
    }

    @Override
    public void showUserPosts(List<ListingKind> posts) {
        adapter.addPosts(posts);
        adapter.notifyDataSetChanged();
    }

}
