package com.rashwan.redditclient.feature.browseFrontPage;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.feature.subredditDetails.SubredditDetailsActivity;
import com.rashwan.redditclient.feature.userDetails.UserDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 9/9/16.
 */

public class BrowseFrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListingKind> posts;
    private Context context;


    public BrowseFrontPageAdapter() {
        this.posts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new BrowseFrontPageVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        ListingKind listingKind = posts.get(position);
        String type = listingKind.getType();
        if (type.equals("post")) {
            RedditPostDataModel post = (RedditPostDataModel) listingKind;
            BrowseFrontPageVH browseFrontPageVH = (BrowseFrontPageVH) holder;
            browseFrontPageVH.title.setText(post.getTitle());
            browseFrontPageVH.points.setText(String.format("%s Points", post.getScore()));
            browseFrontPageVH.comments.setText(String.format(Locale.US, "%d Comments", post.getNumOfComments()));
            browseFrontPageVH.author.setText(post.getAuthor());
            browseFrontPageVH.subreddit.setText(post.getSubreddit());
            if (!post.getThumbnail().isEmpty()) {
                Picasso.with(context).load(post.getThumbnail()).placeholder(R.drawable.ic_reddit_logo_and_wordmark).into(((BrowseFrontPageVH) holder).thumb);
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public void addPosts(List<ListingKind> posts){
        this.posts.addAll(posts);
    }

    public void clearPosts(){
        this.posts.clear();
    }

    public Boolean isEmpty(){
        return this.posts.isEmpty();
    }

    public class BrowseFrontPageVH extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumbnail) ImageView thumb;
        @BindView(R.id.tv_title) TextView title;
        @BindView(R.id.tv_author) TextView author;
        @BindView(R.id.tv_subreddit) TextView subreddit;
        @BindView(R.id.constraint_layout) ConstraintLayout constraintLayout;
        @BindView(R.id.tv_points) TextView points;
        @BindView(R.id.tv_comments) TextView comments;

        public BrowseFrontPageVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.tv_subreddit)
        void onSubredditClicked(TextView view){
            Intent intent = SubredditDetailsActivity
                    .getSubredditDetailsIntent(context,view.getText().toString());
            context.startActivity(intent);
        }
        @OnClick(R.id.tv_author)
        void onAuthorClicked(TextView view){
            Intent intent = UserDetailsActivity.getUserDetailsIntent(context,view.getText().toString());
            context.startActivity(intent);
        }

    }

}
