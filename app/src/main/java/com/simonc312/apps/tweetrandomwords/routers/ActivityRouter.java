package com.simonc312.apps.tweetrandomwords.routers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.simonc312.apps.tweetrandomwords.activities.LoginActivity;
import com.simonc312.apps.tweetrandomwords.activities.MainActivity;

/**
 * Direct activities in app
 * Created by Simon on 4/30/2017.
 */

public class ActivityRouter {

    public static ActivityRouter getRouter(@NonNull final AppCompatActivity fromActivity) {
        return new ActivityRouter(fromActivity);
    }

    private final AppCompatActivity fromActivity;

    private ActivityRouter(@NonNull final AppCompatActivity fromActivity) {
        this.fromActivity = fromActivity;
    }

    public void startLogin() {
        redirectToLogin();
    }

    public void startMain() {
        redirectToMain();
    }

    private void redirectToLogin() {
        fromActivity.startActivity(getRedirectIntent(LoginActivity.class));
        fromActivity.finish();
    }

    private void redirectToMain() {
        fromActivity.startActivity(getRedirectIntent(MainActivity.class));
        fromActivity.finish();
    }

    private Intent getRedirectIntent(@NonNull final Class toLaunch) {
        return new Intent(fromActivity, toLaunch);
    }
}
