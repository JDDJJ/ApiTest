package com.io.jdd.apitest.token.http.exception;

import rx.Observer;

public class RxSubscriber<T> extends ErrorSubscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
//        DialogHelper.showProgressDlg(context, "正在加载数据");
    }

    @Override
    public void onCompleted() {
//        DialogHelper.stopProgressDlg();
    }

    @Override
    protected void onError(ApiException ex) {
//        DialogHelper.stopProgressDlg();
//        Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {

    }
}



