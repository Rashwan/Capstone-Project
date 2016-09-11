package com.rashwan.redditclient.feature.userDetails;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.RedditPost;
import com.rashwan.redditclient.data.model.UserDetailsModel;

import java.util.List;

/**
 * Created by rashwan on 9/11/16.
 */

public interface UserDetailsView extends MvpView{
    void showUserDetails(UserDetailsModel details);
    void showUserPosts(List<RedditPost> posts);
}
