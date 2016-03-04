package com.simonc312.apps.tweetrandomwords.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.activities.UpdateStatusActivity;
import com.simonc312.apps.tweetrandomwords.adapters.TimelineAdapter;
import com.simonc312.apps.tweetrandomwords.helpers.EndlessRVScrollListener;
import com.simonc312.apps.tweetrandomwords.helpers.HorizontalDividerItemDecoration;
import com.simonc312.apps.tweetrandomwords.helpers.ItemClickListener;
import com.simonc312.apps.tweetrandomwords.mixins.EntitiesDeserializer;
import com.simonc312.apps.tweetrandomwords.mixins.TweetMixin;
import com.simonc312.apps.tweetrandomwords.mixins.UserMixin;
import com.simonc312.apps.tweetrandomwords.models.Entities;
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
public class HomeTimelineFragment extends android.support.v4.app.Fragment implements ItemClickListener {

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    TimelineAdapter adapter;
    ObjectReader reader;

    public static HomeTimelineFragment newInstance() {
        return new HomeTimelineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline,container,false);
        ButterKnife.bind(this,view);
        setupRecyclerview(recyclerView);
        setupSwipeRefresh(swipeRefreshLayout);
        setupDeserializer();
        fetchData(false);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setupRecyclerview(final RecyclerView recyclerView) {
        if(adapter == null)
            adapter = new TimelineAdapter(getContext(),new ArrayList<Tweet>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration(getContext().getDrawable(android.R.drawable.divider_horizontal_bright)));
        recyclerView.addOnScrollListener(new EndlessRVScrollListener() {
            @Override
            public void onLoadMore(int current_page) {
                fetchData(false);
            }
        });
    }

    private void setupSwipeRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(true);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
    }

    protected void fetchData(boolean fetchLatest){
        String maxId = adapter.getMaxId();
        String sinceId = fetchLatest ? adapter.getSinceId() : null;
        RestApplication.getRestClient().getHomeTimeline(maxId, sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                handleOnSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(getContext() != null)
                    Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT);
            }
        });
    }

    protected void handleOnSuccess(JSONArray response){
        try {
            //Log.d("log", response.toString());
            List<Tweet> tweetList = reader.readValue(response.toString());
            adapter.addAll(tweetList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDeserializer() {
        SimpleModule simpleModule = new SimpleModule();
        //TODO twitter-text is just really buggy right now. Don't try to use it for auto-linking with entities
        simpleModule.addDeserializer(Entities.class, new EntitiesDeserializer());
        reader = new ObjectMapper()
                .registerModule(simpleModule)
                .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .addMixIn(User.class, UserMixin.class)
                .addMixIn(Tweet.class, TweetMixin.class)
                .reader()
                .with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .forType(new TypeReference<List<Tweet>>() {
                });
    }

    @Override
    public void handleClickEvent(int itemPosition, View itemView, TYPE type) {
        Tweet t = adapter.getItem(itemPosition);
        //saving tweet does not automatically save user and entities...
        t.getUser().save();
        t.save();
        Intent intent = new Intent(getContext(), UpdateStatusActivity.class);
        intent.putExtra("tweetId",t.getTweetId());
        intent.putExtra("type",type);

        //pass shared elements
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), itemView, getString(R.string.item_transition));
        getActivity().startActivityForResult(intent, UpdateStatusActivity.REQUEST_CODE, options.toBundle());
    }
}
