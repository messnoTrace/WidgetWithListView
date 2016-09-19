package com.notrace.model;

import java.util.List;

/**
 * Created by notrace on 2016/9/18.
 */
public class PicResponse {
    private String url;
    private String width;
    private List<Picture> url_list;
    private String uri;
    private String height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public List<Picture> getUrl_list() {
        return url_list;
    }

    public void setUrl_list(List<Picture> url_list) {
        this.url_list = url_list;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
