package com.zzh.service;

import com.zzh.pojo.Videos;
import com.zzh.utils.PagedResult;

import java.util.List;

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

    /**
     * @Description: 分页查询视频列表
     */
    PagedResult getAllVideos(Videos video, Integer isSaveRecord,
                             Integer page, Integer pageSize);

    /**
     * @Description: 获取热搜词列表
     */
    List<String> getHotwords();

    /**
     * @Description: 用户喜欢/点赞视频
     */
    void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 用户不喜欢/取消点赞视频
     */
    void userUnLikeVideo(String userId, String videoId, String videoCreaterId);
}
