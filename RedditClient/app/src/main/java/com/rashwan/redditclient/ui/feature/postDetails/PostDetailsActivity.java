package com.rashwan.redditclient.ui.feature.postDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailsActivity extends AppCompatActivity implements PostDetailsView {


    @BindView(R.id.toolbar_post_details) Toolbar toolbar;
    @BindView(R.id.iv_thumbnail) ImageView thumbnail;
    @BindView(R.id.tv_points) TextView points;
    @BindView(R.id.tv_body) TextView body;
    @BindView(R.id.tv_author) TextView author;
    @BindView(R.id.tv_subreddit) TextView subreddit;
    @BindView(R.id.tv_comments) TextView comments;
    @BindView(R.id.rv_post_comments) RecyclerView rvPostComments;
    private static final String EXTRA_SUBREDDIT = "com.rashwan.redditclient.ui.feature.postDetails.EXTRA_SUBREDDIT";
    private static final String EXTRA_POST_ID = "com.rashwan.redditclient.ui.feature.postDetails.EXTRA_POST_ID";


    @Inject PostDetailsPresenter presenter;
    @Inject PostCommentsAdapter adapter;

    public static Intent getPostDetailsIntent(Context context, String subreddit,String postId){
        Intent intent = new Intent(context,PostDetailsActivity.class);
        intent.putExtra(EXTRA_SUBREDDIT,subreddit);
        intent.putExtra(EXTRA_POST_ID,postId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ((RedditClientApplication)getApplication()).createPostDetailsComponent().inject(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter.attachView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvPostComments.setHasFixedSize(true);
        rvPostComments.addItemDecoration(itemDecoration);
        rvPostComments.setLayoutManager(linearLayoutManager);
        rvPostComments.setAdapter(adapter);
        Intent intent = getIntent();

        presenter.getPostDetails(intent.getStringExtra(EXTRA_SUBREDDIT)
                ,intent.getStringExtra(EXTRA_POST_ID));
    }

    @Override
    public void showPost(RedditPostDataModel post) {
        if (post.body().isEmpty()){
            body.setText(post.title());
        }else {
            body.setText(post.body());
        }
        toolbar.setTitle(post.title());
        points.setText(String.format("%s Points", post.score()));
        comments.setText(String.format(Locale.US, "%d Comments", post.numOfComments()));
        author.setText(post.author());
        subreddit.setText(post.subreddit());
        if (!post.thumbnail().isEmpty()) {
            Picasso.with(this).load(post.thumbnail()).placeholder(R.drawable.ic_reddit_logo_and_wordmark).into(thumbnail);
        }
    }

    @Override
    public void showPostComments(List<ListingKind> comments) {
        adapter.addComments(comments);
        adapter.notifyDataSetChanged();
    }
}
