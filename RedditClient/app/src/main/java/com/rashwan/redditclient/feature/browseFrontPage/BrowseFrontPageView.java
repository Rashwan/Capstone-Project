package com.rashwan.redditclient.feature.browseFrontPage;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.RedditPost;

import java.util.List;

/**
 * Created by rashwan on 9/9/16.
 */

public interface BrowseFrontPageView extends MvpView{
    void showPosts(List<RedditPost> posts);
}
