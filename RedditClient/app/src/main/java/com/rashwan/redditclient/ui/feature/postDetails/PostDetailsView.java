package com.rashwan.redditclient.ui.feature.postDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;

import java.util.List;

/**
 * Created by rashwan on 9/15/16.
 */

public interface PostDetailsView extends MvpView{
    void showPost(RedditPostDataModel post);
    void showPostComments(List<ListingKind> comments);
}
