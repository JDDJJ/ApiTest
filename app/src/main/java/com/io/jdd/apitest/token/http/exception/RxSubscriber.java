package com.io.jdd.apitest.token.http.exception;

import android.content.Context;
import android.widget.Toast;

import rx.Observer;

public class RxSubscriber<T> extends ErrorSubscriber<T> {
//    private Context context;
//
//    public RxSubscriber(Context context) {
//        this.context = context;
//    }
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
//        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        System.err.println(ex.getDisplayMessage()+"--"+ex.getMessage());
    }

    @Override
    public void onNext(T t) {

    }
}



