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

package com.io.jdd.apitest.token;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.io.jdd.apitest.BaseActivity;
import com.io.jdd.apitest.R;
import com.io.jdd.apitest.token.http.GlobalToken;
import com.io.jdd.apitest.token.http.RetrofitUtil;
import com.io.jdd.apitest.token.http.api.IApiService;
import com.io.jdd.apitest.token.http.api.ResultModel;
import com.io.jdd.apitest.token.http.api.TestModel;

import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by david on 16/8/21.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class TokenTestActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_token_test;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.btn_token_get)
    public void onGetTokenClick(View v) {
        RetrofitUtil.getInstance()
            .get(IApiService.class)
            .getToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<TestModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(TestModel model) {
                    System.out.println(GlobalToken.getToken()+"1");
                    System.out.println("111");
                    if (model != null && !TextUtils.isEmpty(model.getData().getToken())) {
                        GlobalToken.updateToken(model.getData().getToken());
                        System.out.println(GlobalToken.getToken());
                    }
                    System.out.println(GlobalToken.getToken()+"2");
                }
            });
    }

    @OnClick(R.id.btn_request)
    public void onRequestClick(View v) {
        for (int i = 0; i < 5; i++) {
            RetrofitUtil.getInstance()
                .get(IApiService.class)
                .getResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultModel model) {

                    }
                });
        }
    }
}
