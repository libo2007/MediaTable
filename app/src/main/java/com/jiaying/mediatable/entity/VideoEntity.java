package com.jiaying.mediatable.entity;

import java.io.Serializable;

/**
 * 作者：lenovo on 2016/3/19 19:52
 * 邮箱：353510746@qq.com
 * 功能：视频实体
 */
public class VideoEntity implements Serializable {
    private String play_url;
    private String cover_url;

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }
}
