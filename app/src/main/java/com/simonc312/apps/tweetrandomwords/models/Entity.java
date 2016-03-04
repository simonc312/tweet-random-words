package com.simonc312.apps.tweetrandomwords.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Simon on 3/3/2016.
 */
@Table(name = "entity")
public class Entity extends Model {
    @Column(name = "start")
    protected int start;
    @Column(name = "end")
    protected int end;
    @Column(name = "value")
    protected String value;
    //unused
    protected String listSlug;

    protected Entity.Type type;
    @Column(name = "display_url")
    protected String displayURL;
    @Column(name = "expanded_url")
    protected String expandedURL;

    /*@Column(name = "entity_id")
    Entities entities;
    */
    public Entity(){
        super();
    }

    public Entity(int start, int end, String value, String listSlug, Entity.Type type) {
        super();
        this.displayURL = null;
        this.expandedURL = null;
        this.start = start;
        this.end = end;
        this.value = value;
        this.listSlug = listSlug;
        this.type = type;
    }

    public Entity(int start, int end, String value, Entity.Type type) {
        this(start, end, value, (String)null, type);
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(!(obj instanceof Entity)) {
            return false;
        } else {
            Entity other = (Entity)obj;
            return this.type.equals(other.type) && this.start == other.start && this.end == other.end && this.value.equals(other.value);
        }
    }

    public int hashCode() {
        return this.type.hashCode() + this.value.hashCode() + this.start + this.end;
    }

    public String toString() {
        return this.value + "(" + this.type + ") [" + this.start + "," + this.end + "]";
    }

    public Integer getStart() {
        return Integer.valueOf(this.start);
    }

    public Integer getEnd() {
        return Integer.valueOf(this.end);
    }

    public String getValue() {
        return this.value;
    }

    public String getListSlug() {
        return this.listSlug;
    }

    public Entity.Type getType() {
        return this.type;
    }

    public String getDisplayURL() {
        return this.displayURL;
    }

    public void setDisplayURL(String displayURL) {
        this.displayURL = displayURL;
    }

    public String getExpandedURL() {
        return this.expandedURL;
    }

    public void setExpandedURL(String expandedURL) {
        this.expandedURL = expandedURL;
    }

    public enum Type {
        URL,
        HASHTAG,
        MENTION,
        CASHTAG;

        Type() {
        }
    }
}
