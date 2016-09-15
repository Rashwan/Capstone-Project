package com.rashwan.redditclient.feature.userDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.UserDetailsModel;
import com.rashwan.redditclient.service.RedditService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 9/11/16.
 */

public class UserDetailsPresenter extends BasePresenter<UserDetailsView> {
    private final RedditService redditService;
    private Subscription detailsSubscription;
    private Subscription postsSubscription;

    public UserDetailsPresenter(RedditService redditService) {
        this.redditService = redditService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (detailsSubscription != null) detailsSubscription.unsubscribe();
        if (postsSubscription != null) postsSubscription.unsubscribe();
    }

    public void getUserDetails(String username){
        checkViewAttached();
        detailsSubscription = redditService.getUserDetails(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingKind -> {
                    if (listingKind.getType().equals(UserDetailsModel.class.getSimpleName())){
                        UserDetailsModel userDetails = (UserDetailsModel) listingKind;
                        Timber.d(userDetails.name());
                        getView().showUserDetails(userDetails);
                    }

                }
                ,Timber::d
                ,() -> Timber.d("completed getting user details"));
    }

    public void getUserPosts(String username){
        checkViewAttached();
        postsSubscription = redditService.getUserPosts(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                    List<ListingKind> posts = listingResponse.data().children();
                    Timber.d(posts.get(0).getType());
                    getView().showUserPosts(posts);
                }
                ,Timber::d
                ,() -> Timber.d("completed getting user posts"));
    }
}
