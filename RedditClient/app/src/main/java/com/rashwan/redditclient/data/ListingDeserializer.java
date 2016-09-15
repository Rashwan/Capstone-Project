package com.rashwan.redditclient.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditCommentDataModel;
import com.rashwan.redditclient.data.model.RedditPostDataModel;
import com.rashwan.redditclient.data.model.SubredditDetailsModel;
import com.rashwan.redditclient.data.model.UserDetailsModel;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by rashwan on 9/14/16.
 */

public class ListingDeserializer implements JsonDeserializer<ListingKind>{

    private static Map<String, Class> map = new TreeMap<>();

    static {
        map.put("t1", RedditCommentDataModel.class);
        map.put("t2", UserDetailsModel.class);
        map.put("t3", RedditPostDataModel.class);
        map.put("t5", SubredditDetailsModel.class);
    }

    @Override
    public ListingKind deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement data = jsonObject.get("data");
        String kind = jsonObject.get("kind").getAsString();
        Class type = map.get(kind);
        if (type == null){
            throw new RuntimeException("Unknown class: " + kind);
        }
        return context.deserialize(data,type);
    }
}
