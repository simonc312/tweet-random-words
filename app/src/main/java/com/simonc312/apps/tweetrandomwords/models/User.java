package com.simonc312.apps.tweetrandomwords.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Simon on 2/28/2016.
 */
@Table(name = "users")
public class User extends Model {
    @Column(name = "username")
    String username;
    @Column(name = "fullname")
    String fullname;
    @Column(name = "profile_image_url")
    String profileImage;
    @Column(name = "background_image_url")
    String backgroundImage;
    @Column(name = "location")
    String location;

    public User(){
        super();
    }

    public User(String username, String fullname, String profileImage, String backgroundImage, String location){
        super();
        this.username = username;
        this.fullname = fullname;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }
}
