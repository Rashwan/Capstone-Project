package com.rashwan.redditclient.feature.subredditDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;
import com.rashwan.redditclient.service.RedditService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 9/10/16.
 */

public class SubredditDetailsPresenter extends BasePresenter<SubredditDetailsView>{
    private final RedditService redditService;
    private Subscription detailsSubscription;
    private Subscription postsSubscription;

    public SubredditDetailsPresenter(RedditService redditService) {
        this.redditService = redditService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (postsSubscription != null) postsSubscription.unsubscribe();
        if (detailsSubscription != null) detailsSubscription.unsubscribe();
    }

    public void getSubredditDetails(String subreddit){
        checkViewAttached();
        detailsSubscription = redditService.getSubredditDetails(subreddit)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingKind -> {
                    if (listingKind.getType().equals(SubredditDetailsModel.class.getSimpleName())){
                        SubredditDetailsModel subredditDetails = (SubredditDetailsModel) listingKind;
                        Timber.d(subredditDetails.name());
                        getView().showSubredditInfo(subredditDetails);
                    }

                }
                    ,Timber::d
                    ,() -> Timber.d("completed subreddit details"));
    }

    public void getSubredditPosts(String subreddit){
        checkViewAttached();
        postsSubscription = redditService.getSubredditPosts(subreddit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                    List<ListingKind> posts = listingResponse.data().children();
                    Timber.d(posts.get(0).getType());
                    getView().showSubredditPosts(posts);
                }
                ,Timber::d
                ,() -> Timber.d("completed subreddit posts"));
    }
}
