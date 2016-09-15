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

/**
 * Created by rashwan on 9/14/16.
 */

public class ListingDeserializer implements JsonDeserializer<ListingKind>{
    @Override
    public ListingKind deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String kind = jsonObject.get("kind").getAsString();
        switch (kind){
            case "t1":
                RedditCommentDataModel comment = context
                        .deserialize(jsonObject.get("data"), RedditCommentDataModel.class);
                comment.setType("comment");
                return comment;
            case "t2":
                UserDetailsModel user = context
                        .deserialize(jsonObject.get("data"), UserDetailsModel.class);
                user.setType("user");
                return user;
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
