package com.simonc312.apps.tweetrandomwords.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Simon on 2/28/2016.
 */
@Table(name = "tweets")
public class Tweet extends Model {
    @Column(name = "status")
    private String status;
    @Column(name = "timestamp")
    private String timeStamp;
    @Column(name = "retweet_count")
    private long retweetCount;
    @Column(name = "favourite_count")
    private long favouriteCount;
    @Column(name = "tweet_id")
    private long tweetId;
    @Column(name = "user")
    private User user;

    private Entities entities;

    public Tweet(){
        super();
    }

    public Tweet(String status, String timeStamp, long retweetCount, long favoritesCount, long tweetId, Entities entities, User user){
        super();
        this.status = status;
        this.timeStamp = timeStamp;
        this.retweetCount = retweetCount;
        this.favouriteCount = favoritesCount;
        this.tweetId = tweetId;
        this.entities = entities;
        this.user = user;
    }

    public String getStatus() {
        return status;
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

    public long getTweetId(){ return tweetId;}

    public List<Entity> getEntities() {
        return entities.getAll();
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public long getFavouriteCount() {
        return favouriteCount;
    }

    public String getDisplayUsername(){
        return String.format("@%s", user.username);
    }

    public static Tweet getTweetById(long tweetId){
        return new Select().from(Tweet.class).where("tweet_id = ? ", tweetId).executeSingle();
    }

    public User getUser() {
        return user;
    }
}
