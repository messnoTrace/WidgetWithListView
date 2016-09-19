package com.notrace.model;

import java.io.Serializable;

/**
 * Created by notrace on 2016/9/13.
 */
public class BaseResponse implements Serializable {
    private String ret;
    private String msg;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
