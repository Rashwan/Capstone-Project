package com.rashwan.redditclient.common;

/**
 * Created by rashwan on 9/8/16.
 */

public interface Presenter <V extends MvpView>{
    void attachView(V view);
    void detachView();
}
