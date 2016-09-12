package com.rashwan.redditclient.feature.browseFrontPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.RedditClientApplication;
import com.rashwan.redditclient.common.utilities.DividerItemDecoration;
import com.rashwan.redditclient.data.model.RedditPost;
import com.rashwan.redditclient.data.model.SubredditDetailsResponse;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BrowseFrontPageActivity extends AppCompatActivity implements BrowseFrontPageView,AdapterView.OnItemSelectedListener {
    @Inject BrowseFrontPagePresenter presenter;
    @Inject BrowseFrontPageAdapter adapter;

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
        rvBrowseFrontPage.setAdapter(adapter);

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        arrayAdapter.add("All");
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);


        presenter.getPopularSubreddits();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void showPosts(List<RedditPost> posts) {
        adapter.clearPosts();
        adapter.addPosts(posts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPopularSubreddits(List<SubredditDetailsResponse> subreddits) {
        for (SubredditDetailsResponse subreddit: subreddits) {
            arrayAdapter.add(subreddit.getData().name());
        }

        adapter.notifyDataSetChanged();
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
}
