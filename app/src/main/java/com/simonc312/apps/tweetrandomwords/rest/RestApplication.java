package com.simonc312.apps.tweetrandomwords.rest;

import android.content.Context;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     RestClient client = RestApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class RestApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		RestApplication.context = this;
	}

	public static RestClient getRestClient() {
		return (RestClient) RestClient.getInstance(RestClient.class, RestApplication.context);
	}

    public static boolean logout(){
        if(getRestClient() != null) {
            getRestClient().clearAccessToken();
            return true;
        }
        return false;
    }

    public static boolean isLoggedIn(){
        return getRestClient().isAuthenticated();
    }


}