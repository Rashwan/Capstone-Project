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
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.UserDetailsModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class UserDetailsActivity extends AppCompatActivity implements UserDetailsView {

    private static final String KEY_POSTS = "POSTS";
    private static final String KEY_POSTS_COUNT = "POSTS_COUNT";
    private static final String KEY_POSTS_AFTER = "POSTS_AFTER";
    private static final String KEY_USER_DETAILS = "USER_DETAILS";
    private static final String EXTRA_USERNAME = "com.rashwan.redditclient.ui.feature.userDetails.EXTRA_USERNAME";

    @BindView(R.id.toolbar_user_details) Toolbar toolbar;
    @BindView(R.id.rv_user_posts) RecyclerView rvUserPosts;
    @Inject UserDetailsPresenter presenter;
    @Inject UserDetailsAdapter adapter;
    private ArrayList<RedditPostDataModel> posts;
    private UserDetailsModel userDetails;


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
        String username = intent.getStringExtra(EXTRA_USERNAME);

        setupRecyclerView(username);
        toolbar.setTitle(username);

        if (savedInstanceState != null){
            posts = savedInstanceState.getParcelableArrayList(KEY_POSTS);
            String after = savedInstanceState.getString(KEY_POSTS_AFTER);
            int count = savedInstanceState.getInt(KEY_POSTS_COUNT);
            userDetails = savedInstanceState.getParcelable(KEY_USER_DETAILS);
            presenter.setCount(count);
            presenter.setAfter(after);
        }

        if (userDetails == null){
            presenter.getUserDetails(username);
        }else {
            showUserDetails(userDetails);
        }
        if (posts == null){
            presenter.getUserPosts(username);
        }else {
            showUserPosts(posts);
        }
    }

    private void setupRecyclerView(String username) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvUserPosts.setHasFixedSize(true);
        rvUserPosts.addItemDecoration(itemDecoration);
        rvUserPosts.setLayoutManager(linearLayoutManager);
        rvUserPosts.setAdapter(adapter);
        rvUserPosts.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager,true) {
            @Override
            public void onLoadMore() {
                if (adapter.getItemCount() != 1) {
                    Timber.d("on load more");
                    presenter.getUserPosts(username);
                }else {
                    Timber.d("this is an empty list with only one item as the header");
                }
            }
        });
    }

    @Override
    public void showUserDetails(UserDetailsModel details) {
        adapter.addUserDetails(details);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showUserPosts(ArrayList<RedditPostDataModel> posts) {
        adapter.addPosts(posts);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((RedditClientApplication)getApplication()).releaseUserDetailsComponent();
        presenter.detachView();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_POSTS,adapter.getPosts());
        outState.putString(KEY_POSTS_AFTER,presenter.getAfter());
        outState.putInt(KEY_POSTS_COUNT,presenter.getCount());
        outState.putParcelable(KEY_USER_DETAILS,adapter.getUserDetails());

        super.onSaveInstanceState(outState);
    }
}
