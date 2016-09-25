package com.rashwan.redditclient.ui.feature.browseFrontPage;

import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.operations.delete.DeleteResult;
import com.pushtorefresh.storio.contentresolver.operations.put.PutResults;
import com.pushtorefresh.storio.contentresolver.queries.DeleteQuery;
import com.pushtorefresh.storio.contentresolver.queries.Query;
import com.rashwan.redditclient.common.BasePresenter;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.provider.RedditPostMeta;
import com.rashwan.redditclient.service.RedditService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rashwan on 9/9/16.
 */

public class BrowseFrontPagePresenter extends BasePresenter<BrowseFrontPageView> {

    private RedditService redditService;
    private StorIOContentResolver storIOContentResolver;
    private Subscription postsSubscription;
    private Subscription subredditsSubscription;
    private Subscription searchPostsSubscription;
    private String currentAfter;
    private String oldSubreddit;
    private int currentCount;

    public BrowseFrontPagePresenter(RedditService redditService,StorIOContentResolver storIOContentResolver) {
        this.redditService = redditService;
        this.storIOContentResolver = storIOContentResolver;
        this.storIOContentResolver = storIOContentResolver;
    }

    public void setCurrentAfter(String currentAfter) {
        this.currentAfter = currentAfter;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public void setOldSubreddit(String oldSubreddit) {
        this.oldSubreddit = oldSubreddit;
    }

    public String getCurrentAfter() {
        return currentAfter;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    @Override
    public void attachView(BrowseFrontPageView view) {
        super.attachView(view);

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
        Timber.d("old subreddit: %s",oldSubreddit);
        Timber.d("method subreddit: %s",subreddit);
        if (!subreddit.equals(oldSubreddit)){
            Timber.d("different subreddit");
            currentAfter = null;
            oldSubreddit = subreddit;
            currentCount = 0;
        }
        postsSubscription = redditService.getSubredditPosts(subreddit,currentAfter,currentCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listingResponse -> {
                    List<ListingKind> posts = listingResponse.data().children();
                    currentAfter = listingResponse.data().after();
                    currentCount += posts.size();
                    Timber.d(posts.get(0).getType());
                    Timber.d(listingResponse.data().before());
                    Timber.d(currentAfter);

                    if (subreddit.equals("All") && listingResponse.data().before() == null) {
                        Observable<List<ListingKind>> getObservable = storIOContentResolver.get()
                                .listOfObjects(ListingKind.class).withQuery(Query.builder()
                                        .uri(RedditPostMeta.CONTENT_URI).build()).prepare()
                                .asRxObservable().observeOn(AndroidSchedulers.mainThread())
                                .take(1).doOnNext(listingKinds -> {
                                    Timber.d(((RedditPostDataModel)listingKinds.get(0)).title());
                                }).doOnCompleted(() ->Timber.d("completed DB chain"));

                        Observable<PutResults<ListingKind>> putObservable = storIOContentResolver
                                .put()
                                .objects(posts).prepare().asRxObservable()
                                .observeOn(AndroidSchedulers.mainThread())
                                .take(1).doOnNext(putResults
                                        -> Timber.d("inserted %d rows"
                                        ,putResults.numberOfInserts()))
                                .doOnCompleted(getObservable::subscribe);

                        Observable<DeleteResult> deleteObservable = storIOContentResolver.delete()
                                .byQuery(DeleteQuery.builder()
                                .uri(RedditPostMeta.CONTENT_URI).build())
                                .prepare().asRxObservable()
                                .observeOn(AndroidSchedulers.mainThread())
                                .take(1).doOnNext(deleteResult
                                    -> Timber.d("deleted %d rows"
                                        ,deleteResult.numberOfRowsDeleted()))
                                .doOnCompleted(putObservable::subscribe);

                        deleteObservable.subscribe();

                    }
                    ArrayList<RedditPostDataModel> convertedPosts = new ArrayList<>();
                    for (ListingKind kind: posts) {
                        convertedPosts.add((RedditPostDataModel) kind);
                    }
                    getView().showPosts(convertedPosts);
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
                    ArrayList<RedditPostDataModel> convertedPosts = new ArrayList<>();
                    for (ListingKind post: posts) {
                        convertedPosts.add((RedditPostDataModel) post);
                    }
                    getView().showSearchResults(convertedPosts);
                }
                ,Timber::d
                ,() -> Timber.d("completed searching posts"));
    }
}
