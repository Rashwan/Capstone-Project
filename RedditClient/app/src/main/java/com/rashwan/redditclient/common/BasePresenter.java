package com.rashwan.redditclient.common;

import static com.rashwan.redditclient.common.utilities.Exceptions.MvpViewNotAttachedException;

/**
 * Created by rashwan on 9/8/16.
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mvpView;
    @Override
    public void attachView(T view) {
        this.mvpView = view;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    private Boolean isViewAttached(){
        return mvpView!=null;
    }

    public T getView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }
}
