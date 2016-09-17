package com.rashwan.redditclient.ui.feature.browseFrontPage;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.ListingKind;
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
    private Subscription postsSubscription;
    private Subscription subredditsSubscription;
    private Subscription searchPostsSubscription;
    private String after;
    private String oldSubreddit;

    public BrowseFrontPagePresenter(RedditService redditService) {
        this.redditService = redditService;
        this.after = null;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (postsSubscription != null) postsSubscription.unsubscribe();
        if (subredditsSubscription != null) subredditsSubscription.unsubscribe();
        if (searchPostsSubscription != null) searchPostsSubscription.unsubscribe();
    }
    public void cancelInFlightRequests(){
        if (postsSubscription != null) postsSubscription.unsubscribe();
        if (searchPostsSubscription != null) searchPostsSubscription.unsubscribe();
    }

    public void getPopularSubreddits(){
        checkViewAttached();
        subredditsSubscription = redditService.getPopularSubreddits()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                    List<ListingKind> subreddits = listingResponse.data().children();
                    Timber.d(subreddits.get(0).getType());
                    getView().showPopularSubreddits(subreddits);
                }
                ,Timber::d
                ,() -> Timber.d("completed getting popular subreddits"));
    }

    public void getSubredditPosts(String subreddit){
        checkViewAttached();
        if (!subreddit.equals(oldSubreddit)){
            Timber.d("different subreddit");
            after = null;
            oldSubreddit = subreddit;
        }
        postsSubscription = redditService.getSubredditPosts(subreddit,after)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                            List<ListingKind> posts = listingResponse.data().children();
                            Timber.d(posts.get(0).getType());
                            after = listingResponse.data().after();
                            Timber.d(after);
                            getView().showPosts(posts);
                        }
                        ,Timber::d
                        ,() -> Timber.d("completed subreddit posts"));
    }

    public void searchPosts(String query){
        checkViewAttached();
        searchPostsSubscription = redditService.searchPosts(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                    List<ListingKind> posts = listingResponse.data().children();
                    Timber.d(posts.get(0).getType());
                    getView().showSearchResults(posts);
                }
                ,Timber::d
                ,() -> Timber.d("completed searching posts"));
    }
}
