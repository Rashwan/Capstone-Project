package com.rashwan.redditclient.feature.subredditDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.SubredditDetails;

/**
 * Created by rashwan on 9/10/16.
 */

public interface SubredditDetailsView extends MvpView {
    void showSubredditInfo(SubredditDetails details);
    void showSubredditPosts();
}
