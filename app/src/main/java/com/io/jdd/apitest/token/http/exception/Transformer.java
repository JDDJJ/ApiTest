package com.io.jdd.apitest.token.http.exception;


import com.io.jdd.apitest.token.http.api.IModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hoomsun on 2017/3/31.
 */

public class Transformer {
    public static <T> Observable.Transformer<IModel<T>, T> sTransformer() {

//        return responseObservable -> responseObservable.map(tResponse -> {
//        if (tResponse.code!=0) throw new RuntimeException();
//        return tResponse.data;
//    }).onErrorResumeNext(new HttpResponseFunc<>());
        return new Observable.Transformer<IModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<IModel<T>> responseObservable) {
                return responseObservable.map(new Func1<IModel<T>, T>() {
                    @Override
                    public T call(IModel<T> tResponse) {
                        System.err.println("my_code"+tResponse.code);
                        if (tResponse.code != 200)
                            throw new HttpTimeException(tResponse.code);
                        return tResponse.data;
                    }
                }).onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> switchSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
            //ExceptionEngine为处理异常的驱动器
//            return Observable.error(new Throwable(throwable));
            return Observable.error(FactoryException.analysisExcetpion(throwable));
        }
    }
}
