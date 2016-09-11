package com.rashwan.redditclient.feature.userDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.UserDetailsModel;
import com.rashwan.redditclient.service.RedditService;

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

    public UserDetailsPresenter(RedditService redditService) {
        this.redditService = redditService;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (detailsSubscription != null) detailsSubscription.unsubscribe();
    }

    public void getUserDetails(String username){
        checkViewAttached();
        detailsSubscription = redditService.getUserDetails(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDetailsResponse -> {
                    UserDetailsModel details = userDetailsResponse.getData();
                    Timber.d(details.name());
                    getView().showUserDetails(details);
                }
                ,Timber::d
                ,() -> Timber.d("completed getting user details"));
    }
}
