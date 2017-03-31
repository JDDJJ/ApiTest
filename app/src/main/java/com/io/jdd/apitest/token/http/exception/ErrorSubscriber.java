package com.io.jdd.apitest.token.http.exception;

import rx.Observer;
import rx.Subscriber;

public abstract class ErrorSubscriber<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            onError((ApiException)e);
        }else{
            onError(new ApiException(e,CodeException.UNKNOWN_ERROR,"未知错误"));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);
}