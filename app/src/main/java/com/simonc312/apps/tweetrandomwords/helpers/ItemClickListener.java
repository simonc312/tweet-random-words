package com.simonc312.apps.tweetrandomwords.helpers;

import android.view.View;

/**
 * Created by Simon on 3/3/2016.
 */
public interface ItemClickListener{
    enum TYPE {REPLY, RETWEET};

    void handleClickEvent(int itemPosition, View itemView, ItemClickListener.TYPE type);
}
