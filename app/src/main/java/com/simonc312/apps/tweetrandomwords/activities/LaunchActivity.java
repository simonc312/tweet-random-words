package com.simonc312.apps.tweetrandomwords.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.rest.RestClient;
import com.simonc312.apps.tweetrandomwords.routers.ActivityRouter;

public class LaunchActivity extends OAuthLoginActionBarActivity<RestClient> {

    @VisibleForTesting
    protected ActivityRouter activityRouter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRouter = ActivityRouter.getRouter(this);
    }

    /**
     * Call made by {@link OAuthLoginActionBarActivity}
     * to authenticate session occurs here.
     */
    @Override
    public void onResume() {
        super.onResume();
        delegateRedirect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.activityRouter = null;
    }

    // OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
        activityRouter.startMain();
    }

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(@NonNull final Exception e) {
		e.printStackTrace();
		Toast.makeText(this, R.string.oauth_failure_toast, Toast.LENGTH_SHORT)
                .show();
		activityRouter.startLogin();
	}

    private void delegateRedirect() {
        if(getClient().isAuthenticated()) {
            activityRouter.startMain();
        } else {
            activityRouter.startLogin();
        }
    }


}
