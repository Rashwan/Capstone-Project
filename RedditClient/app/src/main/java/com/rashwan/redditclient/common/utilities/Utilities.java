package com.rashwan.redditclient.common.utilities;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rashwan on 9/26/16.
 */

public final class Utilities {

    public static Boolean isScreenSW(int smallestWidthDp){
        Configuration config = Resources.getSystem().getConfiguration();
        return config.smallestScreenWidthDp >= smallestWidthDp;
    }
    public static Boolean isNetworkAvailable(Application application){
        ConnectivityManager connectivityManager  = (ConnectivityManager)
                application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
