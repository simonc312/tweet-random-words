package com.simonc312.apps.tweetrandomwords.models;

/**
 * Created by Simon on 2/28/2016.
 */
public class User {
    String username;
    String fullname;
    String profileImage;
    String backgroundImage;
    String location;

    public User(String username, String fullname, String profileImage, String backgroundImage, String location){
        this.username = username;
        this.fullname = fullname;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.location = location;
    }
}
