package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import timber.log.Timber;

/**
 * Created by rashwan on 9/11/16.
 */

@AutoValue
public abstract class UserDetailsModel {
    public abstract String name();
    @Json(name = "created_utc") public abstract long createdUtc();
    @Json(name = "link_karma") public abstract long linkKarma();
    @Json(name = "comment_karma") public abstract long commentKarma();

    public static JsonAdapter<UserDetailsModel> jsonAdapter(Moshi moshi){
        return AutoValue_UserDetailsModel.jsonAdapter(moshi);
    }

    public String convertUtcToLocalTime(Long utc){
        DateFormat date = DateFormat.getDateInstance();
        date.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = date.format(new Date(utc*1000L));
        Timber.d(formatted);
        return formatted;
    }
}
