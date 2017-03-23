package com.io.jdd.apitest.token.http;

import android.util.Log;

import com.io.jdd.apitest.token.http.api.ErrorCode;
import com.io.jdd.apitest.token.http.api.IApiService;
import com.io.jdd.apitest.token.http.api.TestModel;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;

/**
 * 全局自动刷新Token的拦截器
 * <p>
 * 作者：余天然 on 16/9/5 下午3:31
 */
public class TokenInterceptor implements Interceptor {

    private final static int REFRESH_TOKEN_VALID_TIME = 1000;
    private static long tokenChangedTime = 0;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .header("Token", GlobalToken.getToken())
                .build()
                ;
        Response response = chain.proceed(request);
        System.out.println("response.code=" + response.code());
        System.out.print("headers:" + request.headers());
        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期

            //同步请求方式，获取最新的Token
            String newSession = getNewToken();
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("Token", newSession)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == ErrorCode.TOKEN_INVALID) {
            return true;
        }
        return false;
//        try {
//            Gson gson =new Gson();
//            TestModel t = gson.fromJson(response.body().string(),TestModel.class);//此方法会导致response关闭 需要重新build
//
//            System.out.println(t.errorCode);
//            if (t.errorCode == ErrorCode.TOKEN_INVALID||t.errorCode == ErrorCode.TOKEN_INVALID) {
//                return true;//这个return 只会跳出try catch语句
//            }
//            else {
//                return  false;
//            }
//        }catch (Exception e){
//            System.err.println(e.getMessage());
//            return true;
//        }
////        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        // call the refresh token api.
        synchronized (TokenInterceptor.class) {

            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
                return GlobalToken.getToken();
            } else {
                // call the refresh token api.
                System.out.println("静默自动刷新Token,然后重新请求数据");
                RetrofitUtil.getInstance().get(IApiService.class).refreshToken().subscribe(new Subscriber<TestModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TestModel model) {
                        if (model != null) {
                            tokenChangedTime = new Date().getTime();
                            GlobalToken.updateToken(model.getData().getToken());
                            Log.d("Token", "Refresh token success, time = " + tokenChangedTime);
                        }
                    }
                });
                return GlobalToken.getToken();
            }
        }
    }
}