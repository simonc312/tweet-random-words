package com.simonc312.apps.tweetrandomwords.viewholders;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.models.Entities;
import com.simonc312.apps.tweetrandomwords.models.Tweet;
import com.twitter.Autolink;
import com.twitter.Extractor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Bind(R.id.tv_reply)
    TextView tv_reply;
    @Bind(R.id.tv_retweet)
    TextView tv_retweet;
    @Bind(R.id.tv_favorites)
    TextView tv_favorites;
    @Bind(R.id.iv_picture)
    ImageView iv_picture;
    private Linkify.TransformFilter transformFilter;
    private String scheme = "";

    public TweetViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url;
            }
        };
    }

    public void setTweet(Tweet tweet){
        tv_tweet.setText(tweet.getTweet());
        tv_username.setText(tweet.getUsername());
        setImage(tweet.getProfileImage());
        linkifyTweet(tv_tweet, tweet.getEntities());
        updateTextView(tv_retweet,tweet.getRetweetCount());
        updateTextView(tv_favorites, tweet.getFavouritesCount());
    }

    private void linkifyTweet(TextView textView, List<Extractor.Entity> entities) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(Extractor.Entity e : entities){
            sb.append("("+e.getValue()+")|");
        }
        sb.append(")+");
        Linkify.addLinks(textView, Pattern.compile(sb.toString()), scheme, null, transformFilter);
    }

    private void setImage(String url){
        Glide.with(this.itemView.getContext())
                .load(url)
                .centerCrop()
                .into(iv_picture);
    }

    private void updateTextView(TextView textView, long count){
        textView.setEnabled(count > 0);
        textView.setText(String.valueOf(count));
    }
}
