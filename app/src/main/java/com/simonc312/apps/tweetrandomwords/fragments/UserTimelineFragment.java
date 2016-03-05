package com.simonc312.apps.tweetrandomwords.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.rest.RestApplication;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Simon on 3/2/2016.
 */
public class UserTimelineFragment extends HomeTimelineFragment {
    String screenName;

    public static UserTimelineFragment newInstance(String screenName){
        Bundle bundle = new Bundle();
        bundle.putString("screenName", screenName);
        UserTimelineFragment fragment = new UserTimelineFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handleArguments(getArguments());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void fetchData(final boolean fetchLatest){
        String maxId = adapter.getMaxId();
        String sinceId = fetchLatest ? adapter.getSinceId() : null;
        RestApplication.getRestClient().getUserTimeline(screenName, maxId, sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                handleOnSuccess(response, fetchLatest);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT);
            }
        });

    }

    public void handleArguments(Bundle arguments) {
        if(arguments == null) return;
        else{
            screenName = arguments.getString("screenName");
        }
    }
}
