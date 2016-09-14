package com.rashwan.redditclient.data;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by rashwan on 9/14/16.
 */
@GsonTypeAdapterFactory
public abstract class MyAdapterFactory implements TypeAdapterFactory{
    // Static factory method to access the package
    // private generated implementation
    public static TypeAdapterFactory create() {
        return new AutoValueGson_MyAdapterFactory();
    }
}
