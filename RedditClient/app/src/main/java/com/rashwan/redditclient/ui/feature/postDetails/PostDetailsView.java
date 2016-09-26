package com.rashwan.redditclient.ui.feature.postDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.RedditCommentDataModel;
import com.rashwan.redditclient.data.model.RedditPostDataModel;

import java.util.ArrayList;

/**
 * Created by rashwan on 9/15/16.
 */

public interface PostDetailsView extends MvpView{
    void showPost(RedditPostDataModel post);
    void showPostComments(ArrayList<RedditCommentDataModel> comments);
    void showProgress();
    void hideProgress();
    void showOfflineLayout();
    void clearScreen();
}
