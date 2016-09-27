package com.rashwan.redditclient.ui.feature.postDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.common.utilities.Utilities;
import com.rashwan.redditclient.data.model.RedditCommentDataModel;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class PostDetailsActivity extends AppCompatActivity implements PostDetailsView {

    private static final String KEY_COMMENTS = "COMMENTS";
    private static final String KEY_POST_DETAILS = "POST_DETAILS";
    private static final String EXTRA_SUBREDDIT = "com.rashwan.redditclient.ui.feature.postDetails.EXTRA_SUBREDDIT";
    private static final String EXTRA_POST_ID = "com.rashwan.redditclient.ui.feature.postDetails.EXTRA_POST_ID";

    @BindView(R.id.toolbar_post_details) Toolbar toolbar;
    @BindView(R.id.iv_thumbnail) ImageView thumbnail;
    @BindView(R.id.tv_points) TextView points;
    @BindView(R.id.tv_body) TextView body;
    @BindView(R.id.tv_author) TextView author;
    @BindView(R.id.tv_subreddit) TextView subreddit;
    @BindView(R.id.tv_comments) TextView noOfComments;
    @BindView(R.id.rv_post_comments) RecyclerView rvPostComments;
    @BindView(R.id.progressBar_post_details) ProgressBar progressBarPostDetails;
    @BindView(R.id.progressBar_post_comments) ProgressBar progressBarPostComments;
    @BindView(R.id.layout_offline) LinearLayout offlineLayout;
    @BindView(R.id.adView) AdView adView;
    @Inject PostDetailsPresenter presenter;
    @Inject PostCommentsAdapter adapter;

    private ArrayList<RedditCommentDataModel> comments;
    private RedditPostDataModel postDetails;
    private String subredditName;
    private String postId;
    private MenuItem openLinkItem;
    private String url;
    private FirebaseAnalytics firebaseAnalytics;



    public static Intent getPostDetailsIntent(Context context, String subreddit,String postId){
        Intent intent = new Intent(context,PostDetailsActivity.class);
        intent.putExtra(EXTRA_SUBREDDIT,subreddit);
        intent.putExtra(EXTRA_POST_ID,postId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("ON CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ((RedditClientApplication)getApplication()).createPostDetailsComponent().inject(this);
        ButterKnife.bind(this);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544~3347511713");
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter.attachView(this);
        Intent intent = getIntent();
        subredditName = intent.getStringExtra(EXTRA_SUBREDDIT);
        postId = intent.getStringExtra(EXTRA_POST_ID);

        if (savedInstanceState != null){
            postDetails = savedInstanceState.getParcelable(KEY_POST_DETAILS);
            comments = savedInstanceState.getParcelableArrayList(KEY_COMMENTS);
        }

        setupRecyclerView();
        if (postDetails == null || comments == null){
            presenter.getPostDetails(subredditName,postId);
        }else {
            showPost(postDetails);
            showPostComments(comments);
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvPostComments.setHasFixedSize(true);
        rvPostComments.addItemDecoration(itemDecoration);
        rvPostComments.setLayoutManager(linearLayoutManager);
        rvPostComments.setAdapter(adapter);

    }

    @Override
    public void showPost(RedditPostDataModel post) {
        postDetails = post;
        if (post.body().isEmpty()){
            body.setText(post.title());
        }else {
            body.setText(post.body());
        }
        toolbar.setTitle(post.title());
        points.setText(String.format("%s Points", post.score()));
        noOfComments.setText(String.format(Locale.US, "%d Comments", post.numOfComments()));
        author.setText(post.author());
        subreddit.setText(post.subreddit());
        if (!post.thumbnail().isEmpty()) {
            Picasso.with(this).load(post.thumbnail()).placeholder(R.drawable.ic_reddit_logo_and_wordmark).into(thumbnail);
        }
        if (!post.isSelf()){
            openLinkItem.setVisible(true);
            url = post.postUrl();
        }
    }

    @Override
    public void showPostComments(ArrayList<RedditCommentDataModel> comments) {
        adapter.addComments(comments);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBarPostComments.setVisibility(View.VISIBLE);
        progressBarPostDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarPostComments.setVisibility(View.GONE);
        progressBarPostDetails.setVisibility(View.GONE);
    }

    @Override
    public void showOfflineLayout() {
        offlineLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearScreen() {
        progressBarPostDetails.setVisibility(View.GONE);
        progressBarPostComments.setVisibility(View.GONE);
        offlineLayout.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((RedditClientApplication)getApplication()).releasePostDetailsComponent();
        presenter.detachView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_COMMENTS, adapter.getComments());
        outState.putParcelable(KEY_POST_DETAILS,postDetails);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.button_refresh)
    void onRefreshClicked(){
        if (Utilities.isNetworkAvailable(this.getApplication())){
            presenter.getPostDetails(subredditName,postId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Timber.d("ON CREATE MENU");
        getMenuInflater().inflate(R.menu.activity_post_details,menu);
        openLinkItem = menu.findItem(R.id.action_open_link);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_open_link:
                logEventToFA("open in chrome",url);
                Uri uri = Uri.parse(url);
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this,uri);
                break;
        }
        return true;
    }
    private void logEventToFA(String contentType, String itemName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, UUID.randomUUID().toString());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,itemName);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,contentType);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
    }
}
