package com.boostcamp.mytwitter.mytwitter.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.boostcamp.mytwitter.mytwitter.R;
import com.boostcamp.mytwitter.mytwitter.timeline.adapter.TimelineAdapter;
import com.boostcamp.mytwitter.mytwitter.timeline.presenter.TimelinePresenter;
import com.boostcamp.mytwitter.mytwitter.timeline.presenter.TimelinePresenterImpl;
import com.boostcamp.mytwitter.mytwitter.write.WriteActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import twitter4j.User;

public class TimelineActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TimelinePresenter.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tweet_timeline)
    RecyclerView timeline;

    TextView twitterProfileId;
    TextView twitterProfileName;
    ImageView twitterProfileImage;

    private TimelinePresenterImpl presenter;
    private TimelineAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToWrite();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    void init() {
        /**
         * Sidebar Navigation View Setting
         */
        twitterProfileId = (TextView) navigationView.getHeaderView(0).findViewById(R.id.twitter_profile_id);
        twitterProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.twitter_profile_name);
        twitterProfileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.twitter_profile_image);

        mLayoutManager = new LinearLayoutManager(this);
        timeline.setLayoutManager(mLayoutManager);

        mAdapter = new TimelineAdapter(this);
        timeline.setAdapter(mAdapter);


        presenter = new TimelinePresenterImpl();
        presenter.setView(this);
        presenter.setTimelineListAdapterModel(mAdapter);
        presenter.setTimelineListAdapterView(mAdapter);

        presenter.initSidebarNavigation();
        presenter.loadTimelineList();

    }

    void moveToWrite() {
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Sidebar Navigation Data Set
     * @param user
     */
    @Override
    public void initSidebarNavigation(User user) {
        Log.d("TimelineActivity", user.toString());
        twitterProfileId.setText("@" + user.getScreenName());
        twitterProfileName.setText(user.getName());
        Glide.with(this)
             .load(user.getProfileImageURL())
             .into(twitterProfileImage);
    }
}