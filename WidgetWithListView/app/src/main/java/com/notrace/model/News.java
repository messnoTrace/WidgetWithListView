package com.notrace.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by notrace on 2016/9/13.
 */
public class News {
    @SerializedName("abstract")
    private String description;
    private List<PicResponse>image_list;
    private boolean hase_video;
    private int cell_type;
    private String bury_count;
    private String title;
    private String tip;
    private String comment_count;
    private String share_url;
    private String source;
    private String article_url;
    private List<PicResponse>large_image_list;
    private String url;
    private String digg_count;
    private String behot_time;
    private String group_id;
    private PicResponse middle_image;


    public PicResponse getMiddle_image() {
        return middle_image;
    }

    public void setMiddle_image(PicResponse middle_image) {
        this.middle_image = middle_image;
    }

    public boolean isHase_video() {
        return hase_video;
    }

    public void setHase_video(boolean hase_video) {
        this.hase_video = hase_video;
    }

    public int getCell_type() {
        return cell_type;
    }

    public void setCell_type(int cell_type) {
        this.cell_type = cell_type;
    }

    public String getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(String digg_count) {
        this.digg_count = digg_count;
    }

    public String getBury_count() {
        return bury_count;
    }

    public void setBury_count(String bury_count) {
        this.bury_count = bury_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }



    public List<PicResponse> getLarge_image_list() {
        return large_image_list;
    }

    public void setLarge_image_list(List<PicResponse> large_image_list) {
        this.large_image_list = large_image_list;
    }

    public List<PicResponse> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<PicResponse> image_list) {
        this.image_list = image_list;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBehot_time() {
        return behot_time;
    }

    public void setBehot_time(String behot_time) {
        this.behot_time = behot_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}

