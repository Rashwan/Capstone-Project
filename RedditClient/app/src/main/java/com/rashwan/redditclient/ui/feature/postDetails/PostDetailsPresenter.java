package com.rashwan.redditclient.ui.feature.postDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.service.RedditService;

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
        postDetailsSubscription = redditService.getPostDetails(subreddit,postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponses -> {
                    RedditPostDataModel post = (RedditPostDataModel) listingResponses.get(0).data()
                            .children().get(0);
                    Timber.d(post.title());
                    getView().showPost(post);
                    List<ListingKind> comments = listingResponses.get(1).data().children();
                    getView().showPostComments(comments);
                }
                ,Timber::d
                ,() -> Timber.d("completed getting post details"));

    }
}
