package com.rashwan.redditclient.ui.feature.browseFrontPage;

import com.rashwan.redditclient.common.MvpView;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;

import java.util.List;

/**
 * Created by rashwan on 9/9/16.
 */

public interface BrowseFrontPageView extends MvpView{
    void showPosts(List<RedditPostDataModel> posts);
    void showPopularSubreddits(List<ListingKind> subreddits);
    void showSearchResults(List<RedditPostDataModel> posts);
    void showProgress();
    void hideProgress();
    void showOfflineLayout();
    void hideOfflineLayout();
    void clearScreen();
}
