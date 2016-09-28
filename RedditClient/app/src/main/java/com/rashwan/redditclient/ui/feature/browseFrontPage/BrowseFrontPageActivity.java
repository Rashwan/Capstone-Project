package com.rashwan.redditclient.ui.feature.browseFrontPage;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.common.utilities.EndlessRecyclerViewScrollListener;
import com.rashwan.redditclient.common.utilities.Utilities;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;
import com.rashwan.redditclient.ui.common.BrowsePostsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class BrowseFrontPageActivity extends AppCompatActivity implements BrowseFrontPageView,AdapterView.OnItemSelectedListener {
    private static final String KEY_SEARCH_RESULT = "SEARCH";
    private static final String KEY_SEARCH_MODE = "SEARCH_MODE";
    private static final String KEY_SEARCH_QUERY = "SEARCH_QUERY";
    private static final String KEY_POSTS = "POSTS";
    private static final String KEY_POSTS_COUNT = "POSTS_COUNT";
    private static final String KEY_POSTS_AFTER = "POSTS_AFTER";
    private static final String KEY_SUBREDDIT = "SUBREDDIT";
    private static final String KEY_POPULAR_SUBREDDIT = "POPULAR_SUBREDDIT";
    private static final String SUBREDDIT_ALL = "All";

    @Inject BrowseFrontPagePresenter presenter;
    @Inject BrowsePostsAdapter postsAdapter;
    @Inject BrowsePostsAdapter searchAdapter;

    @BindView(R.id.rv_browse_front_page) RecyclerView rvBrowseFrontPage;
    @BindView(R.id.toolbar_browse_front_page) Toolbar toolbar;
    @BindView(R.id.spinner_subreddits) Spinner spinner;
    @BindView(R.id.progressBar_browse_posts) ProgressBar progressBar;
    @BindView(R.id.layout_offline) LinearLayout offlineLayout;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<RedditPostDataModel> posts;
    private String currentSubreddit = SUBREDDIT_ALL;
    private ArrayList<String> popularSubreddits = new ArrayList<>();
    private String searchQuery;
    private boolean isInSearchMode = false;
    private ArrayList<RedditPostDataModel> searchResults = new ArrayList<>();
    private SearchView searchView;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Snackbar snackbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_front_page);
        ((RedditClientApplication)getApplication()).createBrowseFrontPageComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        presenter.attachView(this);
        if (savedInstanceState != null){
            posts = savedInstanceState.getParcelableArrayList(KEY_POSTS);
            String after = savedInstanceState.getString(KEY_POSTS_AFTER);
            int count = savedInstanceState.getInt(KEY_POSTS_COUNT);
            currentSubreddit = savedInstanceState.getString(KEY_SUBREDDIT,SUBREDDIT_ALL);
            popularSubreddits = savedInstanceState.getStringArrayList(KEY_POPULAR_SUBREDDIT);
            isInSearchMode = savedInstanceState.getBoolean(KEY_SEARCH_MODE,false);
            if (isInSearchMode){
                searchQuery = savedInstanceState.getString(KEY_SEARCH_QUERY);
                searchResults = savedInstanceState.getParcelableArrayList(KEY_SEARCH_RESULT);
            }
            presenter.setCurrentAfter(after);
            presenter.setCurrentCount(count);
            presenter.setOldSubreddit(currentSubreddit
            );
        }
        setupRecyclerView();
        setupSpinner();

    }

    private void setupSpinner() {
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        arrayAdapter.add(SUBREDDIT_ALL);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        if (popularSubreddits.isEmpty()){
            presenter.getPopularSubreddits();
        }else {
            arrayAdapter.addAll(popularSubreddits);
            arrayAdapter.notifyDataSetChanged();
            spinner.setSelection(arrayAdapter.getPosition(currentSubreddit));

        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvBrowseFrontPage.setHasFixedSize(true);
        rvBrowseFrontPage.addItemDecoration(itemDecoration);
        rvBrowseFrontPage.setLayoutManager(linearLayoutManager);
        rvBrowseFrontPage.setAdapter(postsAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager,false) {
            @Override
            public void onLoadMore() {
                Timber.d("on load more");
                if (!isInSearchMode) {
                    presenter.getSubredditPosts(spinner.getSelectedItem().toString());
                }
            }};
        rvBrowseFrontPage.addOnScrollListener(scrollListener);
    }


    @Override
    public void showPosts(List<RedditPostDataModel> posts) {
        Timber.d("posts size: %d",posts.size());
        postsAdapter.addPosts(posts);
        postsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPopularSubreddits(List<ListingKind> subreddits) {
        SubredditDetailsModel subredditDetails;
        for (ListingKind subreddit: subreddits) {
            if (subreddit.getType().equals(SubredditDetailsModel.class.getSimpleName())){
                subredditDetails = (SubredditDetailsModel) subreddit;
                arrayAdapter.add(subredditDetails.name());
                popularSubreddits.add(subredditDetails.name());

            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchResults(List<RedditPostDataModel> posts) {
        searchAdapter.clearPosts();
        searchAdapter.addPosts(posts);
        searchAdapter.notifyDataSetChanged();
        rvBrowseFrontPage.swapAdapter(searchAdapter,true);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showOfflineLayout() {
        offlineLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOfflineLayout() {
        offlineLayout.setVisibility(View.GONE);
    }

    @Override
    public void clearScreen() {
        progressBar.setVisibility(View.GONE);
        offlineLayout.setVisibility(View.GONE);
        if(snackbar != null){
            snackbar.dismiss();
        }
    }

    @Override
    public void showOfflineSnackBar() {
        snackbar = Snackbar.make(coordinatorLayout, R.string.msg_snackbar_no_internet
                ,Snackbar.LENGTH_INDEFINITE)
                .setAction("refresh", view -> presenter.getSubredditPosts(currentSubreddit));
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((RedditClientApplication)getApplication()).releaseBrowseFrontPageComponent();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList(KEY_SEARCH_RESULT,searchAdapter.getPosts());
        outState.putParcelableArrayList(KEY_POSTS,postsAdapter.getPosts());
        outState.putString(KEY_POSTS_AFTER,presenter.getCurrentAfter());
        outState.putInt(KEY_POSTS_COUNT,presenter.getCurrentCount());
        outState.putString(KEY_SUBREDDIT, currentSubreddit);
        outState.putStringArrayList(KEY_POPULAR_SUBREDDIT,popularSubreddits);
        outState.putBoolean(KEY_SEARCH_MODE,isInSearchMode);
        outState.putString(KEY_SEARCH_QUERY,searchView.getQuery().toString());

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String subreddit = (String) parent.getAdapter().getItem(position);
        presenter.cancelInFlightRequests();
        postsAdapter.clearPosts();
        postsAdapter.notifyDataSetChanged();
        if (posts != null && currentSubreddit.equals(subreddit)){
            showPosts(posts);
        }else {
            currentSubreddit = subreddit;
            presenter.getSubredditPosts(subreddit);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Timber.d("nothing selected");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_browse_front_page,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.cancelInFlightRequests();
                if (!searchResults.isEmpty() && query.equals(searchQuery)){
                    showSearchResults(searchResults);
                }else {
                    presenter.searchPosts(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Timber.d("search view expanded");
                isInSearchMode = true;
                searchAdapter.clearPosts();
                rvBrowseFrontPage.swapAdapter(searchAdapter,true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Timber.d("search view collapsed");
                isInSearchMode = false;
                searchQuery = "";
                scrollListener.reset();
                if (!postsAdapter.isEmpty()){
                    hideOfflineLayout();
                    rvBrowseFrontPage.swapAdapter(postsAdapter,true);
                }else {
                    rvBrowseFrontPage.swapAdapter(postsAdapter,true);
                    presenter.getSubredditPosts(currentSubreddit);
                }
                return true;
            }
        });
        if (isInSearchMode){
            menuItem.expandActionView();
            searchView.setQuery(searchQuery,true);
        }

        return true;
    }
    @OnClick(R.id.button_refresh)
    void onRefreshClicked(){
        if (Utilities.isNetworkAvailable(this.getApplication())){
            presenter.getPopularSubreddits();
            presenter.getSubredditPosts(currentSubreddit);
            if (isInSearchMode){
                presenter.searchPosts(searchView.getQuery().toString());
            }
        }
    }
}
