package com.rashwan.redditclient.common.utilities;

/**
 * Created by rashwan on 9/9/16.
 */

public final class Exceptions {
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }
}
