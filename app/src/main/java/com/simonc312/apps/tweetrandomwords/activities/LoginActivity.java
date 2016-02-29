package com.simonc312.apps.tweetrandomwords.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.rest.RestApplication;
import com.simonc312.apps.tweetrandomwords.rest.RestClient;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends OAuthLoginActionBarActivity<RestClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        checkIfLaunchMain();
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
	}

    private void checkIfLaunchMain() {
        if(RestApplication.isLoggedIn())
            startActivity(new Intent(this, MainActivity.class));
    }


    // Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		// Intent i = new Intent(this, PhotosActivity.class);
		// startActivity(i);
		Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
        checkIfLaunchMain();
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
    @OnClick(R.id.btn_login)
	public void loginToRest(View view) {
		getClient().connect();
	}

}
