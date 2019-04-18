package com.zzh.service;

import com.zzh.pojo.Bgm;
import com.zzh.pojo.Comments;
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
    PagedResult getAllVideos(Videos video, Integer isSaveRecord, String loginUserId,
                             Integer page, Integer pageSize);

    /**
     * @Description: 查询我喜欢的视频列表
     */
    PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 查询我关注的人的视频列表
     */
    PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);

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

    /**
     * @Description: 用户留言
     */
    void saveComment(Comments comment);

    void likeComment(String userId, String commentId);

    void unlikeComment(String userId, String commentId);

    /**
     * @Description: 留言分页
     */
    PagedResult getAllComments(String videoId,String loginUserId, Integer page, Integer pageSize);

    PagedResult queryReportList(Integer page, Integer pageSize);

    void updateVideoStatus(String videoId, Integer status);

    PagedResult queryBgmList(Integer page, Integer pageSize);

    String addBgm(Bgm bgm);

    void deleteBgm(String id);

}
