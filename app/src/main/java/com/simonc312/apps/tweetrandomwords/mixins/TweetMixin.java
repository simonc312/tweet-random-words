package com.simonc312.apps.tweetrandomwords.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simonc312.apps.tweetrandomwords.models.User;

/**
 * Created by Simon on 2/28/2016.
 */
public abstract class TweetMixin {

    TweetMixin(@JsonProperty("text") String text,
               @JsonProperty("created_at") String timeStamp,
               @JsonProperty("user") User user) {}
}
