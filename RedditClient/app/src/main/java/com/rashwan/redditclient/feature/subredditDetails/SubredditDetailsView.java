package com.rashwan.redditclient.feature.subredditDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;

import java.util.List;

/**
 * Created by rashwan on 9/10/16.
 */

public interface SubredditDetailsView extends MvpView {
    void showSubredditInfo(SubredditDetailsModel details);
    void showSubredditPosts(List<ListingKind> posts);
}
