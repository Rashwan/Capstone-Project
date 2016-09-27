package com.rashwan.redditclient.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rashwan.redditclient.R;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.ui.feature.postDetails.PostDetailsActivity;
import com.rashwan.redditclient.ui.feature.subredditDetails.SubredditDetailsActivity;
import com.rashwan.redditclient.ui.feature.userDetails.UserDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 9/9/16.
 */

public class BrowsePostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RedditPostDataModel> posts;
    private Context context;
    private final FirebaseAnalytics firebaseAnalytics;



    public BrowsePostsAdapter(FirebaseAnalytics firebaseAnalytics) {
        this.posts = new ArrayList<>();
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_post, parent, false);

        return new BrowsePostsVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        ListingKind listingKind = posts.get(position);
        String type = listingKind.getType();
        if (type.equals(RedditPostDataModel.class.getSimpleName())) {
            RedditPostDataModel post = (RedditPostDataModel) listingKind;
            BrowsePostsVH browsePostsVH = (BrowsePostsVH) holder;
            browsePostsVH.title.setText(post.title());
            browsePostsVH.points.setText(String.format("%s Points", post.score()));
            browsePostsVH.comments.setText(String.format(Locale.US, "%d Comments", post.numOfComments()));
            browsePostsVH.author.setText(post.author());
            browsePostsVH.subreddit.setText(post.subreddit());
            if (!post.thumbnail().isEmpty()) {
                Picasso.with(context).load(post.thumbnail()).placeholder(R.drawable.ic_reddit_logo_and_wordmark).into(((BrowsePostsVH) holder).thumb);
            }
            browsePostsVH.constraintLayout.setOnClickListener(v -> {
                browsePostsVH.logEventToFA("post",post.title());
                Intent intent = PostDetailsActivity.getPostDetailsIntent(context,
                        post.subreddit(), post.id());

                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public void addPosts(List<RedditPostDataModel> posts){
        this.posts.addAll(posts);
    }

    public ArrayList<RedditPostDataModel> getPosts() {
        return posts;
    }

    public void clearPosts(){
        this.posts.clear();
    }

    public Boolean isEmpty(){
        return this.posts.isEmpty();
    }

    public class BrowsePostsVH extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumbnail) ImageView thumb;
        @BindView(R.id.tv_title) TextView title;
        @BindView(R.id.tv_author) TextView author;
        @BindView(R.id.tv_subreddit) TextView subreddit;
        @BindView(R.id.constraint_layout) ConstraintLayout constraintLayout;
        @BindView(R.id.tv_points) TextView points;
        @BindView(R.id.tv_comments) TextView comments;

        public BrowsePostsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.tv_subreddit)
        void onSubredditClicked(TextView view){
            logEventToFA("subreddit",view.getText().toString());

            Intent intent = SubredditDetailsActivity
                    .getSubredditDetailsIntent(context,view.getText().toString());
            context.startActivity(intent);
        }
        @OnClick(R.id.tv_author)
        void onAuthorClicked(TextView view){
            logEventToFA("user",view.getText().toString());

            Intent intent = UserDetailsActivity.getUserDetailsIntent(context
                    ,view.getText().toString());
            context.startActivity(intent);
        }

        private void logEventToFA(String contentType, String itemName) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, UUID.randomUUID().toString());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,itemName);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,contentType);
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
        }


    }

}
