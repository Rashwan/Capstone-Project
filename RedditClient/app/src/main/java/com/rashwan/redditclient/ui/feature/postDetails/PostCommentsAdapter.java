package com.rashwan.redditclient.ui.feature.postDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditCommentDataModel;
import com.rashwan.redditclient.ui.feature.userDetails.UserDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rashwan on 9/15/16.
 */

public class PostCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<ListingKind> comments;

    public PostCommentsAdapter() {
        this.comments = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new PostCommentsVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListingKind listingKind = comments.get(position);
        String type = listingKind.getType();
        if (type.equals(RedditCommentDataModel.class.getSimpleName())) {
            RedditCommentDataModel comment = (RedditCommentDataModel) listingKind;

            PostCommentsAdapter.PostCommentsVH postsCommentsVH = (PostCommentsVH) holder;
            postsCommentsVH.body.setText(comment.body());
            postsCommentsVH.points.setText(String.format("%s Points", comment.score()));
            postsCommentsVH.author.setText(comment.author());
            if (comment.getReplies()!=null){
                RedditCommentDataModel firstReply = (RedditCommentDataModel) comment.getReplies()
                        .data().children().get(0);
                postsCommentsVH.firstReplyLayout.setVisibility(View.VISIBLE);
                postsCommentsVH.firstReplyAuthor.setText(firstReply.author());
                postsCommentsVH.firstReplyBody.setText(firstReply.body());
                postsCommentsVH.firstReplyPoints.setText(String.format("%s Points", firstReply.score()));

                if (comment.getReplies().data().children().size() > 1){
                    RedditCommentDataModel secondReply = (RedditCommentDataModel) comment.getReplies()
                            .data().children().get(1);
                    postsCommentsVH.secondReplyLayout.setVisibility(View.VISIBLE);
                    postsCommentsVH.secondReplyAuthor.setText(secondReply.author());
                    postsCommentsVH.secondReplyBody.setText(secondReply.body());
                    postsCommentsVH.secondReplyPoints.setText(String.format("%s Points", secondReply.score()));
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addComments(List<ListingKind> comments){
        this.comments.addAll(comments);
    }

    public void clearComments(){
        this.comments.clear();
    }

    public Boolean isEmpty(){
        return this.comments.isEmpty();
    }


    public class PostCommentsVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_points) TextView points;
        @BindView(R.id.tv_body) TextView body;
        @BindView(R.id.tv_author) TextView author;
        @BindView(R.id.tv_first_reply_points) TextView firstReplyPoints;
        @BindView(R.id.tv_first_reply_body) TextView firstReplyBody;
        @BindView(R.id.tv_first_reply_author) TextView firstReplyAuthor;
        @BindView(R.id.first_reply_layout) LinearLayout firstReplyLayout;
        @BindView(R.id.tv_second_reply_points) TextView secondReplyPoints;
        @BindView(R.id.tv_second_reply_body) TextView secondReplyBody;
        @BindView(R.id.tv_second_reply_author) TextView secondReplyAuthor;
        @BindView(R.id.second_reply_layout) LinearLayout secondReplyLayout;


        public PostCommentsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.tv_author)
        void onAuthorClicked(TextView view){
            Intent intent = UserDetailsActivity.getUserDetailsIntent(context,view.getText().toString());
            context.startActivity(intent);
        }

    }
}
