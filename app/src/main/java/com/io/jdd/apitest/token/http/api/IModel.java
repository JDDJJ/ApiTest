package com.io.jdd.apitest.token.http.api;

/**
 * 标准数据格式
 * @param <T>
 */
public class IModel<T> {
    public int code;
    public String message;
    public T data;
}