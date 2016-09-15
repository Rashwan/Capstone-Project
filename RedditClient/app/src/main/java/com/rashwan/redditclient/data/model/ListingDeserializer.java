package com.rashwan.redditclient.data.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import timber.log.Timber;

/**
 * Created by rashwan on 9/14/16.
 */

public class ListingDeserializer implements JsonDeserializer<ListingKind>{
    @Override
    public ListingKind deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Timber.d(json.toString());
        JsonObject jsonObject = json.getAsJsonObject();
        String kind = jsonObject.get("kind").getAsString();
        switch (kind){
            case "t1":
                RedditCommentDataModel comment = context
                        .deserialize(jsonObject.get("data"), RedditCommentDataModel.class);
                comment.setType("comment");
                return comment;
            case "t3":
                RedditPostDataModel post = context
                        .deserialize(jsonObject.get("data"), RedditPostDataModel.class);
                post.setType("post");
                return post;
            case "t5":
                SubredditDetailsModel subreddit = context
                        .deserialize(jsonObject.get("data"),SubredditDetailsModel.class);
                subreddit.setType("subreddit");
                return subreddit;
        }
        return null;
    }
}
