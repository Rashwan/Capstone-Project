package com.rashwan.redditclient.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.rashwan.redditclient.data.model.ListingResponse;
import com.rashwan.redditclient.data.model.RedditCommentDataModel;

import java.lang.reflect.Type;

import timber.log.Timber;

/**
 * Created by rashwan on 9/16/16.
 */

public class CommentDeserializer implements JsonDeserializer<RedditCommentDataModel>{
    private final Gson gson;
    public CommentDeserializer() {
        this.gson = new GsonBuilder()
            .registerTypeAdapterFactory(MyAdapterFactory.create())
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    }

    @Override
    public RedditCommentDataModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement repliesJson = json.getAsJsonObject().get("replies");
        RedditCommentDataModel comment = gson.fromJson(json,RedditCommentDataModel.class);
        if (!repliesJson.isJsonObject()){
            Timber.d("replies are empty");
            comment.setReplies(null);
        }else {
            Timber.d("we have replies");
            comment.setReplies(context.deserialize(repliesJson, ListingResponse.class));
        }
        return comment;
    }
}
