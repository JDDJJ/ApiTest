package com.io.jdd.apitest.token.http.api;

/**
 * Created by hoomsun on 2017/3/21.
 */

public class TestModel {

    /**
     * data : {"token":"767oTdNPU0m0Ui6D3bOQ9uTQ"}
     * code : 0
     * message : success
     */

    private DataBean data;
    private int code;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
