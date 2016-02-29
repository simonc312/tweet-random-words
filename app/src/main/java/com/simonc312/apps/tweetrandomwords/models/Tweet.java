package com.simonc312.apps.tweetrandomwords.models;

/**
 * Created by Simon on 2/28/2016.
 */
public class Tweet {

    private String tweet;
    private User user;
    private String timeStamp;

    public Tweet(String tweet, String timeStamp, User user){
        this.tweet = tweet;
        this.timeStamp = timeStamp;
        this.user = user;
    }

    public String getTweet() {
        return tweet;
    }

    public String getUsername() {
        return user.username;
    }

    public String getProfileImage() {
        return user.profileImage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
