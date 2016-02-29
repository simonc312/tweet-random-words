package com.simonc312.apps.tweetrandomwords.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.models.Tweet;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 2/28/2016.
 */
public class TweetViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_tweet)
    TextView tv_tweet;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.iv_picture)
    ImageView iv_picture;

    public TweetViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setTweet(Tweet tweet){
        tv_tweet.setText(tweet.getTweet());
        tv_username.setText(tweet.getUsername());
        setImage(tweet.getProfileImage());
    }

    private void setImage(String url){
        Glide.with(this.itemView.getContext())
                .load(url)
                .centerCrop()
                .into(iv_picture);
    }
}
