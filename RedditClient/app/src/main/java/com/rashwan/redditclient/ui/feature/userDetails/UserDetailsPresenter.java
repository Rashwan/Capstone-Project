package com.rashwan.redditclient.ui.feature.userDetails;

import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.UserDetailsModel;
import com.rashwan.redditclient.service.RedditService;

import java.util.ArrayList;
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
    private String after;
    private int count;
    boolean firstRequest = true;

    public UserDetailsPresenter(RedditService redditService) {
        this.redditService = redditService;
        this.after = null;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
        if (!firstRequest && after == null){
            Timber.d("we have all the user posts");
            return;
        }
        checkViewAttached();
        postsSubscription = redditService.getUserPosts(username,after,count)
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
                    getView().showUserPosts(convertedPosts);
                }
                ,Timber::d
                ,() -> Timber.d("completed getting user posts"));
    }
}
