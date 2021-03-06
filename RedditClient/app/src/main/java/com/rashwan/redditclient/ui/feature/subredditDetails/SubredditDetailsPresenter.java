package com.rashwan.redditclient.ui.feature.subredditDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.common.utilities.Exceptions;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;
import com.rashwan.redditclient.service.RedditService;

import java.util.ArrayList;
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
    private String after;
    private int count;
    private boolean firstRequest = true;

    public SubredditDetailsPresenter(RedditService redditService) {
        this.redditService = redditService;
        this.after = null;
    }

    public String getAfter() {
        return after;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (postsSubscription != null) postsSubscription.unsubscribe();
        if (detailsSubscription != null) detailsSubscription.unsubscribe();
    }

    public void getSubredditDetails(String subreddit){
        checkViewAttached();
        getView().showSubredditInfoProgress();
        detailsSubscription = redditService.getSubredditDetails(subreddit)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingKind -> {
                    if (listingKind.getType().equals(SubredditDetailsModel.class.getSimpleName())){
                        SubredditDetailsModel subredditDetails = (SubredditDetailsModel) listingKind;
                        Timber.d(subredditDetails.name());
                        getView().hideSubredditInfoProgress();
                        getView().showSubredditInfo(subredditDetails);
                    }

                }
                    ,Timber::d
                    ,() -> Timber.d("completed subreddit details"));
    }

    public void getSubredditPosts(String subreddit){
        if (!firstRequest && after == null){
            Timber.d("we have all the user posts");
            return;
        }
        checkViewAttached();
        getView().clearScreen();
        if (count == 0){
            getView().showSubredditPostsProgress();
        }
        postsSubscription = redditService.getSubredditPosts(subreddit,after,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                    List<ListingKind> posts = listingResponse.data().children();
                    Timber.d(posts.get(0).getType());
                    after = listingResponse.data().after();
                    count += posts.size();
                    firstRequest = false;
                    Timber.d(after);
                    ArrayList<RedditPostDataModel> convertedPosts = new ArrayList<>();
                    for (ListingKind post : posts) {
                        convertedPosts.add((RedditPostDataModel) post);
                    }
                    getView().hideSubredditPostsProgress();
                    getView().showSubredditPosts(convertedPosts);
                }
                ,throwable -> {
                        if (throwable instanceof Exceptions.NoInternetException) {
                            Exceptions.NoInternetException exception = (Exceptions.NoInternetException) throwable;
                            Timber.d("Error retrieving posts: %s . First page: %s"
                                    , exception.message, exception.firstPage);
                            if (exception.firstPage) {
                                getView().hideSubredditPostsProgress();
                                getView().showOfflineLayout();
                            } else {
                                getView().showOfflineSnackBar();
                            }
                        }
                    }
                ,() -> Timber.d("completed subreddit posts"));
    }
}
