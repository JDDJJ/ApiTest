/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.io.jdd.apitest.token.http;

import java.lang.reflect.Proxy;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 16/8/19.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class RetrofitUtil implements IGlobalManager {

    //    public static final String API = "http://192.168.56.1:8888/";
    public static final String API = "http://192.168.1.110:8888/";
//    public static final String API = "http://192.168.1.227:9000";

    private volatile static Retrofit sRetrofit;
    private static OkHttpClient sOkHttpClient;
    private static RetrofitUtil instance;

    private final static Object mRetrofitLock = new Object();

    private static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (mRetrofitLock) {
        if (sRetrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new TokenInterceptor());
            sOkHttpClient = clientBuilder.build();
            sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                    .baseUrl(API)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                }
            }
        }
        return sRetrofit;
    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }

    public <T> T get(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    @Override
    public void exitLogin() {
        // Cancel all the netWorkRequest
        sOkHttpClient.dispatcher().cancelAll();

//        // Goto the home page
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(BaseApplication.getContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                BaseApplication.getContext().startActivity(intent);
//                Toast.makeText(BaseApplication.getContext(), "Token is not existed!!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
