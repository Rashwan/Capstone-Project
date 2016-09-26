package com.rashwan.redditclient.ui.feature.subredditDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.common.utilities.EndlessRecyclerViewScrollListener;
import com.rashwan.redditclient.common.utilities.RoundedTransformation;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;
import com.rashwan.redditclient.ui.common.BrowsePostsAdapter;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SubredditDetailsActivity extends AppCompatActivity implements SubredditDetailsView {

    private static final String KEY_POSTS = "POSTS";
    private static final String KEY_POSTS_COUNT = "POSTS_COUNT";
    private static final String KEY_POSTS_AFTER = "POSTS_AFTER";
    private static final String KEY_SUBREDDIT_DETAILS = "SUBREDDIT_DETAILS";
    private static final String EXTRA_SUBREDDIT = "com.rashwan.redditclient.ui.feature.subredditDetails.EXTRA_SUBREDDIT";

    @BindView(R.id.iv_subreddit_icon) ImageView subredditIcon;
    @BindView(R.id.tv_subreddit_name) TextView subredditName;
    @BindView(R.id.tv_subreddit_description) TextView subredditDescription;
    @BindView(R.id.tv_subreddit_subscribers) TextView subredditSubscribers;
    @BindView(R.id.toolbar_subreddit_details) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_subreddit_details) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.rv_subreddit_posts) RecyclerView rvSubredditPosts;
    @Inject SubredditDetailsPresenter presenter;
    @Inject BrowsePostsAdapter adapter;

    private ArrayList<RedditPostDataModel> posts;
    private String after;
    private int count;
    private SubredditDetailsModel subredditDetails;


    public static Intent getSubredditDetailsIntent(Context context,String subreddit){
        Intent intent = new Intent(context,SubredditDetailsActivity.class);
        intent.putExtra(EXTRA_SUBREDDIT,subreddit);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreddit_details);
        ((RedditClientApplication)getApplication()).createSubredditDetailsComponent().inject(this);
        ButterKnife.bind(this);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String subreddit = intent.getStringExtra(EXTRA_SUBREDDIT);
        presenter.attachView(this);

        if (savedInstanceState != null){
            posts = savedInstanceState.getParcelableArrayList(KEY_POSTS);
            after = savedInstanceState.getString(KEY_POSTS_AFTER);
            count = savedInstanceState.getInt(KEY_POSTS_COUNT);
            subredditDetails = savedInstanceState.getParcelable(KEY_SUBREDDIT_DETAILS);
            presenter.setAfter(after);
            presenter.setCount(count);
        }
        setupRecyclerView(subreddit);
        if (subredditDetails == null){
            presenter.getSubredditDetails(subreddit);
        }else {
            showSubredditInfo(subredditDetails);
        }
        if (posts == null){
            presenter.getSubredditPosts(subreddit);
        }else {
            showSubredditPosts(posts);
        }

    }

    private void setupRecyclerView(String subreddit) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvSubredditPosts.setHasFixedSize(true);
        rvSubredditPosts.addItemDecoration(itemDecoration);
        rvSubredditPosts.setLayoutManager(linearLayoutManager);
        rvSubredditPosts.setAdapter(adapter);
        rvSubredditPosts.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager,false) {
            @Override
            public void onLoadMore() {
                Timber.d("on load more");
                presenter.getSubredditPosts(subreddit);
            }
        });
    }


    @Override
    public void showSubredditInfo(SubredditDetailsModel details) {
        subredditDetails = details;
        subredditName.setText(details.name());
        subredditDescription.setText(details.description());
        String formattedNumber = NumberFormat.getInstance().format(details.numOfSubscribers());
        subredditSubscribers.setText(String.format(Locale.US,"%s Subscribers",formattedNumber));

        int color = ContextCompat.getColor(this,android.R.color.white);
        String iconUrl = details.subredditIcon().isEmpty()?null:details.subredditIcon();
        Picasso.with(this)
                .load(iconUrl)
                .placeholder(R.drawable.ic_reddit_logo_and_wordmark)
                .transform(new RoundedTransformation(color)).into(subredditIcon);
        toolbar.setTitle(details.name());
    }

    @Override
    public void showSubredditPosts(List<RedditPostDataModel> posts) {
        adapter.addPosts(posts);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((RedditClientApplication)getApplication()).releaseSubredditDetailsComponent();
        presenter.detachView();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_POSTS,adapter.getPosts());
        outState.putString(KEY_POSTS_AFTER,presenter.getAfter());
        outState.putInt(KEY_POSTS_COUNT,presenter.getCount());
        outState.putParcelable(KEY_SUBREDDIT_DETAILS,subredditDetails);
        super.onSaveInstanceState(outState);
    }
}
