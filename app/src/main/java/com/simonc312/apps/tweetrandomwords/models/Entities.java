package com.simonc312.apps.tweetrandomwords.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 3/2/2016.
 */
public class Entities {

    List<Entity> entityList;
    public Entities(){
        entityList = new ArrayList<>();
    }

    public void addList(List<Entity> list){
        entityList.addAll(list);
    }

    public List<Entity> getAll() {
        return entityList;
    }
}
