package com.rashwan.redditclient.feature.subredditDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.SubredditDetails;
import com.rashwan.redditclient.service.RedditService;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 9/10/16.
 */

public class SubredditDetailsPresenter extends BasePresenter<SubredditDetailsView>{
    private RedditService redditService;
    private Subscription postsSubscribtion;

    public SubredditDetailsPresenter(RedditService redditService) {
        this.redditService = redditService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (postsSubscribtion != null) postsSubscribtion.unsubscribe();
    }

    public void getSubredditDetails(String subreddit){
        checkViewAttached();
        postsSubscribtion = redditService.getSubredditDetails(subreddit)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subredditDetailsResponse -> {
                    SubredditDetails data = subredditDetailsResponse.getData();
                    Timber.d(data.name());
                    getView().showSubredditInfo(data);
                }
                    ,Timber::d
                    ,() -> Timber.d("completed subreddit details"));
    }
}
