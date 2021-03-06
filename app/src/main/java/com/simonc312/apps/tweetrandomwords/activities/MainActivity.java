package com.simonc312.apps.tweetrandomwords.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.simonc312.apps.tweetrandomwords.customviews.NavHeaderUser;
import com.simonc312.apps.tweetrandomwords.fragments.HomeTimelineFragment;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.fragments.UserTimelineFragment;
import com.simonc312.apps.tweetrandomwords.models.User;
import com.simonc312.apps.tweetrandomwords.rest.RestApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Simon on 2/28/2016.
 */
public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_FRAGMENT_TAG = "currentFragmentTag";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpActionBar();
        setUpNavDrawer();
        selectDefaultDrawerItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == UpdateStatusActivity.REQUEST_CODE){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT_TAG);
            if(fragment instanceof HomeTimelineFragment){
                ((HomeTimelineFragment) fragment).fetchData(true);
            }
        }
    }

    @OnClick(R.id.fab)
    public void handleFabClick(){
        startActivityForResult(new Intent(this,UpdateStatusActivity.class),UpdateStatusActivity.REQUEST_CODE);
    }

    private void setUpActionBar() {
        setSupportActionBar(mToolbar);
    }


    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.menu);
            setUpNavDrawerListener();
        }
    }


    private void setUpNavDrawerListener(){
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_logout:
                        if(RestApplication.logout()) finish();
                        break;
                    default:
                        selectDrawerItem(item);
                }
                return false;
            }
        });
    }

    public void selectDefaultDrawerItem(){
        selectDrawerItem(mNavigationView.getMenu().findItem(R.id.action_timeline));
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment;

        switch(menuItem.getItemId()) {
            case R.id.action_timeline:
                fragment = HomeTimelineFragment.newInstance();
                break;
            case R.id.action_your_tweets:
                User user = RestApplication.getAuthenticatedUser();
                fragment = UserTimelineFragment.newInstance(user.getUsername());
                break;
            default:
                fragment = HomeTimelineFragment.newInstance();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_container, fragment,CURRENT_FRAGMENT_TAG).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        getSupportActionBar().setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();

    }

}
