package com.simonc312.apps.tweetrandomwords.models;

import com.twitter.Extractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 3/2/2016.
 */
public class Entities {
    List<Extractor.Entity> entityList;

    public Entities(){
        entityList = new ArrayList<>();
    }

    public void addList(List<Extractor.Entity> list){
        entityList.addAll(list);
    }

    public List<Extractor.Entity> getAll() {
        return entityList;
    }
}
