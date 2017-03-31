package com.io.jdd.apitest.token.http.exception;

import rx.Subscriber;

/**
 * Created by hoomsun on 2017/3/31.
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
