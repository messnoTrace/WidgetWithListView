package com.notrace.model;

import java.util.List;

/**
 * Created by notrace on 2016/9/13.
 */
public class NewsResponse extends BaseResponse {
    private List<News>data;
    private boolean has_more;

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }
}
