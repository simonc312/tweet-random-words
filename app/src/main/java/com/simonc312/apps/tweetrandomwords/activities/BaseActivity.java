package com.simonc312.apps.tweetrandomwords.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.simonc312.apps.tweetrandomwords.routers.ActivityRouter;

/**
 * Created by Simon on 4/30/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    protected ActivityRouter activityRouter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRouter = ActivityRouter.getRouter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.activityRouter = null;
    }
}
