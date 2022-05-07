package com.tencent.liteav.demo.player.expand.model.entity;

import java.util.ArrayList;
import java.util.List;

public class VideoListModel {
    public List<VideoModel> videoModelList = new ArrayList<>();
    public String title;
    public String icon;

    public int size() {
        return videoModelList.size();
    }

    public VideoModel get(int index) {
        return videoModelList.get(index);
    }

    public void addVideoModel(VideoModel videoModel) {
        videoModelList.add(videoModel);
    }
}
