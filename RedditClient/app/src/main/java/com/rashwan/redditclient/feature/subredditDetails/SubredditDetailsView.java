package com.rashwan.redditclient.feature.subredditDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.RedditPost;
import com.rashwan.redditclient.data.model.SubredditDetails;

import java.util.List;

/**
 * Created by rashwan on 9/10/16.
 */

public interface SubredditDetailsView extends MvpView {
    void showSubredditInfo(SubredditDetails details);
    void showSubredditPosts(List<RedditPost> posts);
}
