package com.simonc312.apps.tweetrandomwords.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.fragments.HomeTimelineFragment;
import com.simonc312.apps.tweetrandomwords.helpers.ItemClickListener;
import com.simonc312.apps.tweetrandomwords.viewholders.TweetViewHolder;
import com.simonc312.apps.tweetrandomwords.models.Tweet;

import java.util.List;

/**
 * Created by Simon on 2/28/2016.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    private final Context context;
    private final ItemClickListener listener;
    List<Tweet> tweets;

    public TimelineAdapter(Context context, List<Tweet> tweets, ItemClickListener listener){
        this.context = context;
        this.tweets = tweets;
        this.listener = listener;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timeline, parent, false);
        return new TweetViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.setTweet(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void addAll(List<Tweet> newList, int position){
        tweets.addAll(position, newList);
        notifyItemRangeInserted(position,newList.size());
    }

    public void addAllEnd(List<Tweet> newList){
        addAll(newList, tweets.size());
    }

    public void addAllFront(List<Tweet> tweetList) {
        addAll(tweetList, 0);
    }

    /**
     *
     *
     * @return last tweet's id adjusted by one or null if empty.
     */
    @Nullable
    public String getMaxId(){
        if(tweets.isEmpty()) return null;
        return String.valueOf(tweets.get(tweets.size()-1).getTweetId() - 1);
    }

    /**
     *
     * @return the greatest id processed (at beginning of the list)
     */
    public String getSinceId() {
        if(tweets.isEmpty()) return null;
        else return String.valueOf(tweets.get(0).getTweetId());
    }

    public Tweet getItem(int adapterPosition) {
        return tweets.get(adapterPosition);
    }


}
