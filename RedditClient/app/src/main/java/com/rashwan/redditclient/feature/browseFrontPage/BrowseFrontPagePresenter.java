package com.rashwan.redditclient.feature.browseFrontPage;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.RedditPost;
import com.rashwan.redditclient.service.RedditService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 9/9/16.
 */

public class BrowseFrontPagePresenter extends BasePresenter<BrowseFrontPageView> {

    private RedditService redditService;
    private Subscription postsSubscribtion;

    public BrowseFrontPagePresenter(RedditService redditService) {
        this.redditService = redditService;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (postsSubscribtion != null) postsSubscribtion.unsubscribe();
    }

    public void getPosts(){
        checkViewAttached();
        postsSubscribtion = redditService.getHotPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                            List<RedditPost> posts = listingResponse.getData().getPosts();
                            Timber.d(posts.get(0).getRedditPostData().title());
                            getView().showPosts(posts);
                        }
                        , Timber::d
                        , () -> Timber.d("Completed hot"));
    }
}
