package com.simonc312.apps.tweetrandomwords.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.adapters.TimelineAdapter;
import com.simonc312.apps.tweetrandomwords.mixins.TweetMixin;
import com.simonc312.apps.tweetrandomwords.mixins.UserMixin;
import com.simonc312.apps.tweetrandomwords.models.Tweet;
import com.simonc312.apps.tweetrandomwords.models.User;
import com.simonc312.apps.tweetrandomwords.rest.RestApplication;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 2/28/2016.
 */
public class HomeTimelineFragment extends android.support.v4.app.Fragment{

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    TimelineAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_timeline,container,false);
        ButterKnife.bind(this,view);
        setupRecyclerview(recyclerView);
        fetchData();
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setupRecyclerview(RecyclerView recyclerView) {
        if(adapter == null)
            adapter = new TimelineAdapter(getContext(),new ArrayList<Tweet>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void fetchData(){
        RestApplication.getRestClient().getHomeTimeline(null,null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ObjectReader reader = new ObjectMapper()
                        .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .addMixIn(User.class, UserMixin.class)
                        .addMixIn(Tweet.class, TweetMixin.class)
                        .reader()
                        .with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                        .forType(new TypeReference<List<Tweet>>() {});
                try {
                    List<Tweet> tweetList = reader.readValue(response.toString());
                    adapter.addAll(tweetList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
