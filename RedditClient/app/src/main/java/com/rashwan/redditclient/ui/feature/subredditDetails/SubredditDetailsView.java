package com.rashwan.redditclient.ui.feature.subredditDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;

import java.util.List;

/**
 * Created by rashwan on 9/10/16.
 */

public interface SubredditDetailsView extends MvpView {
    void showSubredditInfo(SubredditDetailsModel details);
    void showSubredditPosts(List<RedditPostDataModel> posts);
    void showSubredditPostsProgress();
    void hideSubredditPostsProgress();
    void showSubredditInfoProgress();
    void hideSubredditInfoProgress();
}
