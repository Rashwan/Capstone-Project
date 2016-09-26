package com.rashwan.redditclient.ui.feature.userDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.UserDetailsModel;

import java.util.ArrayList;

/**
 * Created by rashwan on 9/11/16.
 */

public interface UserDetailsView extends MvpView{
    void showUserDetails(UserDetailsModel details);
    void showUserPosts(ArrayList<RedditPostDataModel> posts);
}
