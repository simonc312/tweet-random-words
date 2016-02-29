package com.simonc312.apps.tweetrandomwords.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Simon on 2/28/2016.
 */
public abstract class UserMixin {

    UserMixin(@JsonProperty("screen_name") String username,
              @JsonProperty("name") String fullname,
              @JsonProperty("profile_image_url") String profileImage,
              @JsonProperty("profile_background_image_url") String backgroundImage,
              @JsonProperty("location") String location){}

}
