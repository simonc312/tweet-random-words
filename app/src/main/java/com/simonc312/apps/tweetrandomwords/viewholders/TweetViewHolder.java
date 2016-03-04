package com.simonc312.apps.tweetrandomwords.viewholders;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.helpers.ItemClickListener;
import com.simonc312.apps.tweetrandomwords.models.Entity;
import com.simonc312.apps.tweetrandomwords.models.Tweet;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private ItemClickListener listener;

    public TweetViewHolder(View itemView, ItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        ButterKnife.bind(this, itemView);
        transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url;
            }
        };
    }

    /**
     * This constructor will hide actions retweet, reply, favourite
     * @param itemView
     */
    public TweetViewHolder(View itemView) {
        this(itemView, null);
        hideActions();
    }

    @OnClick(R.id.tv_reply)
    public void handleReplyClick(){
        listener.handleClickEvent(getAdapterPosition(), ItemClickListener.TYPE.REPLY);
    }

    @OnClick(R.id.tv_retweet)
    public void handleRetweetClick(){
        listener.handleClickEvent(getAdapterPosition(),ItemClickListener.TYPE.RETWEET);
    }

    public void setTweetOnly(Tweet tweet){
        tv_tweet.setText(Html.fromHtml(tweet.getStatus()));
        tv_username.setText(tweet.getDisplayUsername());
        setImage(tweet.getProfileImage());
    }

    public void setTweet(Tweet tweet){
        setTweetOnly(tweet);
        linkifyTweet(tv_tweet, tweet.getEntities());
        updateTextView(tv_retweet, tweet.getRetweetCount());
        updateTextView(tv_favorites, tweet.getFavouriteCount());
    }

    private void linkifyTweet(TextView textView, List<Entity> entities) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(Entity e : entities){
            sb.append("((@|#)*"+e.getValue()+")|");
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

    private void hideActions() {
        tv_reply.setVisibility(View.GONE);
        tv_retweet.setVisibility(View.GONE);
        tv_favorites.setVisibility(View.GONE);
    }
}
