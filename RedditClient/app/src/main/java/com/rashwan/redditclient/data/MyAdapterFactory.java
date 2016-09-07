package com.rashwan.redditclient.data;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

/**
 * Created by rashwan on 9/7/16.
 */

@MoshiAdapterFactory
public abstract class MyAdapterFactory implements JsonAdapter.Factory{

    public static JsonAdapter.Factory create(){
        return new AutoValueMoshi_MyAdapterFactory();
    }
}
