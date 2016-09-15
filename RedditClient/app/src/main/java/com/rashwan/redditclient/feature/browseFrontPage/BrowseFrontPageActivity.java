package com.rashwan.redditclient.feature.browseFrontPage;

import android.os.Bundle;
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
import android.widget.Spinner;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BrowseFrontPageActivity extends AppCompatActivity implements BrowseFrontPageView,AdapterView.OnItemSelectedListener {
    @Inject BrowseFrontPagePresenter presenter;
    @Inject BrowseFrontPageAdapter postsAdapter;
    @Inject BrowseFrontPageAdapter searchAdapter;


    @BindView(R.id.rv_browse_front_page) RecyclerView rvBrowseFrontPage;
    @BindView(R.id.toolbar_browse_front_page) Toolbar toolbar;
    @BindView(R.id.spinner_subreddits) Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_front_page);
        ((RedditClientApplication)getApplication()).createBrowseFrontPageComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        presenter.attachView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        rvBrowseFrontPage.setHasFixedSize(true);
        rvBrowseFrontPage.addItemDecoration(itemDecoration);
        rvBrowseFrontPage.setLayoutManager(linearLayoutManager);
        rvBrowseFrontPage.setAdapter(postsAdapter);

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        arrayAdapter.add("All");
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);


        presenter.getPopularSubreddits();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void showPosts(List<ListingKind> posts) {
        postsAdapter.clearPosts();
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
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchResults(List<ListingKind> posts) {
        searchAdapter.clearPosts();
        searchAdapter.addPosts(posts);
        searchAdapter.notifyDataSetChanged();
        rvBrowseFrontPage.swapAdapter(searchAdapter,true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((RedditClientApplication)getApplication()).releaseBrowseFrontPageComponent();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String subreddit = (String) parent.getAdapter().getItem(position);
        presenter.getSubredditPosts(subreddit);
        Timber.d(subreddit);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Timber.d("nothing selected");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_browse_front_page,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchPosts(query);
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
                searchAdapter.clearPosts();
                rvBrowseFrontPage.swapAdapter(searchAdapter,true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Timber.d("search view collapsed");
                rvBrowseFrontPage.swapAdapter(postsAdapter,true);
                return true;
            }
        });


        return true;
    }
}
