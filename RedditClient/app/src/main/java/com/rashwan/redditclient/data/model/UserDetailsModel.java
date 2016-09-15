package com.rashwan.redditclient.data.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import timber.log.Timber;

/**
 * Created by rashwan on 9/11/16.
 */

public class UserDetailsModel extends ListingKind{
    public String name;
    @SerializedName("created_utc") public long createdUtc;
    @SerializedName("link_karma") public long linkKarma;
    @SerializedName("comment_karma") public long commentKarma;

    public String convertUtcToLocalTime(Long utc){
        DateFormat date = DateFormat.getDateInstance();
        date.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = date.format(new Date(utc*1000L));
        Timber.d(formatted);
        return formatted;
    }

    public String getName() {
        return name;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public long getLinkKarma() {
        return linkKarma;
    }

    public long getCommentKarma() {
        return commentKarma;
    }
}
