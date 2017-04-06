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
import android.view.View;

import com.io.jdd.apitest.BaseActivity;
import com.io.jdd.apitest.R;
import com.io.jdd.apitest.token.http.GlobalToken;
import com.io.jdd.apitest.token.http.RetrofitUtil;
import com.io.jdd.apitest.token.http.api.DataBean;
import com.io.jdd.apitest.token.http.api.IApiService;
import com.io.jdd.apitest.token.http.exception.RxSubscriber;
import com.io.jdd.apitest.token.http.exception.Transformer;

import butterknife.OnClick;

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
                .getToken().compose(Transformer.<DataBean>sTransformer())
                .compose(Transformer.<DataBean>switchSchedulers())
                .subscribe(new RxSubscriber<DataBean>(){
                    /**
                     *
                     * 这里时自定义的订阅者 已经处理的对onError onStart 的模块  只需要重写 onNext parse数据就好 而如果不需要进度条什么的  错误处理想用其他方式处理 可通过继承ErrorSubscriber 或者用rx的原生也可以
                     * @param dataBean
                     */
                    @Override
                    public void onNext(DataBean dataBean) {
                        super.onNext(dataBean);
                        System.out.println(dataBean.getToken());//输出
                        GlobalToken.updateToken(dataBean.getToken());//第一此获取
                    }
                });
//        RetrofitUtil.getInstance()
//            .get(IApiService.class)
//            .getToken()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Subscriber<TestModel>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onNext(TestModel model) {
//                    System.out.println(GlobalToken.getToken()+"1");
//                    System.out.println("111");
//                    if (model != null && !TextUtils.isEmpty(model.getData().getToken())) {
//                        GlobalToken.updateToken(model.getData().getToken());
//                        System.out.println(GlobalToken.getToken());
//                    }
//                    System.out.println(GlobalToken.getToken()+"2");
//                }
//            });
    }

    /**
     * 访问接口 token失效  服务端是不会返回新的token的 客户端必须自己去取
     * @param v
     */
    @OnClick(R.id.btn_request)
    public void onRequestClick(View v) {
        for (int i = 0; i < 5; i++) {
            RetrofitUtil.getInstance()
                .get(IApiService.class)
                .getResult()
                .compose(Transformer.<DataBean>sTransformer())
                    .compose(Transformer.<DataBean>switchSchedulers())
                .subscribe(new RxSubscriber<DataBean>(){
                    @Override
                    public void onNext(DataBean dataBean) {
                        super.onNext(dataBean);
                        System.err.println(dataBean.getToken());
                    }
                });
        }
    }
}
