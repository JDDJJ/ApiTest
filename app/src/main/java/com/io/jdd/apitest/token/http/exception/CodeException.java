package com.io.jdd.apitest.token.http.exception;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 自定义错误code类型:注解写法
 * <p>
 * 可自由扩展
 * Created by WZG on 2016/12/12.
 */

public class CodeException {

    /*网络错误*/
    public static final int NETWORD_ERROR = 201;
    /*http_错误*/
    public static final int HTTP_ERROR = 202;
    /*fastjson错误*/
    public static final int JSON_ERROR = 203;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 204;
    /*运行时异常-包含自定义异常*/
    public static final int RUNTIME_ERROR = 205;
    /*无法解析该域名*/
    public static final int UNKOWNHOST_ERROR = 206;

    public static final int Payment_Required = 402;

    @IntDef({NETWORD_ERROR, HTTP_ERROR, RUNTIME_ERROR, UNKNOWN_ERROR, JSON_ERROR, UNKOWNHOST_ERROR,Payment_Required})
    @Retention(RetentionPolicy.SOURCE)

    public @interface CodeEp {
    }

}
