package com.rashwan.redditclient.feature.browseFrontPage;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.data.model.RedditPost;
import com.rashwan.redditclient.data.model.RedditPostData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 9/9/16.
 */

public class BrowseFrontPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RedditPost> posts;

    public BrowseFrontPageAdapter() {
        this.posts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new BrowseFrontPageVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        RedditPost post = posts.get(position);
        RedditPostData postData = post.getRedditPostData();
        BrowseFrontPageVH browseFrontPageVH = (BrowseFrontPageVH) holder;
        browseFrontPageVH.title.setText(postData.title());
        browseFrontPageVH.points.setText(postData.score());
        browseFrontPageVH.comments.setText(String.valueOf(postData.numOfComments()));
        browseFrontPageVH.userSubreddit.setText(String.format("by %s in %s",postData.author(),postData.subreddit()));
        Picasso.with(context).load(postData.thumbnail()).into(((BrowseFrontPageVH) holder).thumb);
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public void addPosts(List<RedditPost> posts){
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
        @BindView(R.id.tv_user_subreddit) TextView userSubreddit;
        @BindView(R.id.constraint_layout) ConstraintLayout constraintLayout;
        @BindView(R.id.tv_points) TextView points;
        @BindView(R.id.tv_comments) TextView comments;

        public BrowseFrontPageVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
