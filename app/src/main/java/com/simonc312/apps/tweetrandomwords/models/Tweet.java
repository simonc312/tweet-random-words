package com.simonc312.apps.tweetrandomwords.models;

import com.twitter.Extractor;

import java.util.List;

/**
 * Created by Simon on 2/28/2016.
 */
public class Tweet {

    private String tweet;
    private User user;
    private String timeStamp;
    private long id;
    private Entities entities;

    public Tweet(String tweet, String timeStamp, long id, Entities entities, User user){
        this.tweet = tweet;
        this.timeStamp = timeStamp;
        this.id = id;
        this.entities = entities;
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

    public long getId(){ return id;}

    public List<Extractor.Entity> getEntities() {
        return entities.getAll();
    }
}
