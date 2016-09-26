package com.rashwan.redditclient.ui.feature.postDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.common.utilities.Exceptions;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditCommentDataModel;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.service.RedditService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 9/15/16.
 */

public class PostDetailsPresenter extends BasePresenter<PostDetailsView> {
    private RedditService redditService;
    private Subscription postDetailsSubscription;

    public PostDetailsPresenter(RedditService redditService) {
        this.redditService = redditService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (postDetailsSubscription != null) postDetailsSubscription.unsubscribe();
    }

    void getPostDetails(String subreddit, String postId){
        checkViewAttached();
        getView().clearScreen();
        getView().showProgress();
        postDetailsSubscription = redditService.getPostDetails(subreddit,postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponses -> {
                    RedditPostDataModel post = (RedditPostDataModel) listingResponses.get(0).data()
                            .children().get(0);
                    Timber.d(post.title());
                    getView().hideProgress();
                    getView().showPost(post);
                    List<ListingKind> comments = listingResponses.get(1).data().children();
                    ArrayList<RedditCommentDataModel> convertedComments = new ArrayList<>();
                    for (ListingKind comment: comments) {
                        convertedComments.add((RedditCommentDataModel) comment);
                    }
                    getView().showPostComments(convertedComments);
                }
                , throwable -> {
                    if (throwable instanceof Exceptions.NoInternetException) {
                        Exceptions.NoInternetException exception = (Exceptions.NoInternetException) throwable;
                        Timber.d("Error retrieving posts: %s .", exception.message);
                        getView().hideProgress();
                        getView().showOfflineLayout();
                    }
                }
                ,() -> Timber.d("completed getting post details"));

    }
}
