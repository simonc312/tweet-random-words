package com.simonc312.apps.tweetrandomwords.rest;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class RestClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "hXhntXEt0tKECI5rSLvRf9U43";       // Change this
	public static final String REST_CONSUMER_SECRET = "zBsqrAS6ohev6woLtmIYKmG1T5WBh84SE1PDz3WyZl5VdUOzWu"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://tweetsimonc312"; // Change this (here and in manifest)

	public RestClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	/*public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}*/

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

	public void getAccountInfo(JsonHttpResponseHandler handler){
		String apiUrl = getApiUrl("/account/verify_credentials.json");
		client.get(apiUrl,handler);
	}

	public void getHomeTimeline(String max_id, String since_id, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
        params.put("count",42);
		if(max_id != null)
			params.put("max_id",max_id); //need to decrement this id by 1 because its inclusive
		if(since_id != null)
			params.put("since_id",since_id); //set this to the last id tweet received
        client.get(apiUrl, params, handler);
	}

}