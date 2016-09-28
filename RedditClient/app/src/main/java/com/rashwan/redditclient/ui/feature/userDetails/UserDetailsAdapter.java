package com.rashwan.redditclient.ui.feature.userDetails;

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
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.UserDetailsModel;
import com.rashwan.redditclient.ui.feature.postDetails.PostDetailsActivity;
import com.rashwan.redditclient.ui.feature.subredditDetails.SubredditDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 9/17/16.
 */

public class UserDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<RedditPostDataModel> posts;
    private UserDetailsModel userDetails;
    private Context context;
    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_POST = 1;


    public UserDetailsAdapter() {
        this.posts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == VIEW_TYPE_HEADER){
            view = inflater.inflate(R.layout.item_user_details,parent,false);
            return new UserDetailsVH(view);
        }else if (viewType == VIEW_TYPE_POST){
            view = inflater.inflate(R.layout.item_post, parent, false);
            return new BrowsePostsVH(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if (holder instanceof BrowsePostsVH) {

            RedditPostDataModel post = getPost(position);
            BrowsePostsVH browsePostsVH = (BrowsePostsVH) holder;
            browsePostsVH.title.setText(post.title());
            browsePostsVH.points.setText(String.format("%s Points", post.score()));
            browsePostsVH.comments.setText(String.format(Locale.getDefault(), "%d Comments", post.numOfComments()));
            browsePostsVH.author.setText(post.author());
            browsePostsVH.subreddit.setText(post.subreddit());
            if (!post.thumbnail().isEmpty()) {
                Picasso.with(context).load(post.thumbnail()).placeholder(R.drawable.ic_reddit_logo_and_wordmark).into(((BrowsePostsVH) holder).thumb);
            }

            browsePostsVH.constraintLayout.setOnClickListener(v -> {
                Intent intent = PostDetailsActivity.getPostDetailsIntent(context,
                        post.subreddit(), post.id());
                context.startActivity(intent);
            });

        }else if (holder instanceof UserDetailsVH){
            if (userDetails != null) {
                UserDetailsVH userDetailsVH = (UserDetailsVH) holder;
                userDetailsVH.username.setText(userDetails.name());
                userDetailsVH.postKarma.setText(String.format(Locale.getDefault(), "Link Karma: %d Points", userDetails.linkKarma()));
                userDetailsVH.commentCarma.setText(String.format(Locale.getDefault(), "Comment Karma: %d Points", userDetails.commentKarma()));
                userDetailsVH.joinedAt.setText(String.format(Locale.getDefault(),"Joined At: %s",userDetails.convertUtcToLocalTime(userDetails.createdUtc())));
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.posts.size() +1 ;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)){
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_POST;
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }
    private RedditPostDataModel getPost(int position) {
        return posts.get(position - 1);
    }

    public void addPosts(ArrayList<RedditPostDataModel> posts){
        this.posts.addAll(posts);
    }
    public void addUserDetails(UserDetailsModel userDetails){
        this.userDetails = userDetails;
    }

    public UserDetailsModel getUserDetails() {
        return userDetails;
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
    public class UserDetailsVH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_username) TextView username;
        @BindView(R.id.tv_user_post_carma) TextView postKarma;
        @BindView(R.id.tv_user_comment_carma) TextView commentCarma;
        @BindView(R.id.tv_user_joined_at) TextView joinedAt;
        public UserDetailsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
