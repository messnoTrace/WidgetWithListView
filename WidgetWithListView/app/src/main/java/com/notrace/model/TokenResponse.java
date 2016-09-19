package com.notrace.model;

/**
 * Created by notrace on 2016/9/13.
 */
public class TokenResponse extends BaseResponse {
    private NewsToken data;

    public NewsToken getData() {
        return data;
    }

    public void setData(NewsToken data) {
        this.data = data;
    }
}
