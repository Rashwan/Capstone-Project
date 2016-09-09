package com.rashwan.redditclient.common;

/**
 * Created by rashwan on 9/8/16.
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {
    @Override
    public void attachView(T view) {

    }

    @Override
    public void detachView() {

    }
}
