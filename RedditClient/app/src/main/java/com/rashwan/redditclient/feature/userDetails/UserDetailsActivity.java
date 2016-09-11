package com.rashwan.redditclient.feature.userDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.data.model.UserDetailsModel;

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
    @Inject
    UserDetailsPresenter presenter;

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
        ((RedditClientApplication) getApplication()).createUserDetailsComponent().inject(this);
        presenter.attachView(this);
        Intent intent = getIntent();
        presenter.getUserDetails(intent.getStringExtra(EXTRA_USERNAME));
    }

    @Override
    public void showUserDetails(UserDetailsModel details) {
        username.setText(details.name());
        userPostCarma.setText(String.format(Locale.US,"Link Karma: %d Points",details.linkKarma()));
        userCommentCarma.setText(String.format(Locale.US,"Comment Karma: %d Points",details.commentKarma()));
        userJoinedAt.setText(details.convertUtcToLocalTime(details.createdUtc()));
    }

}
