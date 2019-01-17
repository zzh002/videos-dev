package com.zzh.service;

import com.zzh.pojo.Videos;

/**
 * @author ZZH
 * @date 2019/1/17 15:14
 **/
public interface VideoService {
    /**
     * @Description: 保存视频
     */
    String saveVideo(Videos video);

    /**
     * @Description: 修改视频的封面
     */
    void updateVideo(String videoId, String coverPath);
}
