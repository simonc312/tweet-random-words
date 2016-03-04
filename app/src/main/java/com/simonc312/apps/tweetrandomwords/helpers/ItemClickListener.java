package com.simonc312.apps.tweetrandomwords.helpers;

/**
 * Created by Simon on 3/3/2016.
 */
public interface ItemClickListener{
    enum TYPE {REPLY, RETWEET};

    void handleClickEvent(int itemPosition, ItemClickListener.TYPE type);
}
