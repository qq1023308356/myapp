package com.example.coolnews.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by xiong on 17-5-24.
 */

public class news implements MultiItemEntity {
    public static final int TEXT = 0;
    public static final int IMG = 1;
    private int itemType;

    private String imgsrc;
    private String source;
    private String title;
    private String url;
    private String ptime;
    private List<news> newsList;

    public List<news> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<news> newsList) {
        this.newsList = newsList;
    }

    public news(int itemType) {
        this.itemType = itemType;
    }
    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
}
