package com.simonc312.apps.tweetrandomwords.mixins;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.simonc312.apps.tweetrandomwords.models.Entities;
import static com.twitter.Extractor.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 3/2/2016.
 */
public class EntitiesDeserializer extends JsonDeserializer<Entities> {

    @Override
    public Entities deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Entities entities = new Entities();
        JsonNode node = p.readValueAsTree();
        JsonNode urlNode = node.get("urls");
        JsonNode hashtagNode = node.get("hashtags");
        JsonNode mentionsNode = node.get("user_mentions");

        entities.addList(getEntities(urlNode, Entity.Type.URL));
        entities.addList(getEntities(hashtagNode, Entity.Type.HASHTAG));
        entities.addList(getEntities(mentionsNode, Entity.Type.MENTION));
        return entities;
    }

    private List<Entity> getEntities(JsonNode node, Entity.Type type){
        int size = node == null ? 0 : node.size();
        List<Entity> list = new ArrayList<>(size);
        for(int i=0;i<size;i++){
            JsonNode e = node.get(i);
            JsonNode indices = e.get("indices");
            int start = indices.get(0).asInt();
            int end = indices.get(1).asInt();
            Entity entity = null;
            switch(type){
                case URL:
                    entity = new Entity(
                            start,
                            end,
                            e.get("url").asText(),
                            type);
                    entity.setDisplayURL(e.get("display_url").asText());
                    entity.setExpandedURL(e.get("expanded_url").asText());
                    break;
                case HASHTAG:
                    entity = new Entity(
                            start,
                            end,
                            e.get("text").asText(),
                            type
                    );
                    break;
                case MENTION:
                    entity = new Entity(
                            start,
                            end,
                            e.get("screen_name").asText(),
                            type
                    );
                    break;
            }
            list.add(entity);
        }
        return list;
    }
}
