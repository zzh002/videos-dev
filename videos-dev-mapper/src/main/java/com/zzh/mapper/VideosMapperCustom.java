package com.zzh.mapper;

import com.zzh.pojo.Videos;
import com.zzh.pojo.vo.VideosVO;
import com.zzh.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	/**
	 * @Description: 条件查询所有视频列表
	 */
	List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc,
                                         @Param("userId") String userId);
	
	/**
	 * @Description: 查询关注的视频
	 */
	List<VideosVO> queryMyFollowVideos(String userId);
	
	/**
	 * @Description: 查询点赞视频
	 */
	List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);
	
	/**
	 * @Description: 对视频喜欢的数量进行累加
	 */
	void addVideoLikeCount(String videoId);
	
	/**
	 * @Description: 对视频喜欢的数量进行累减
	 */
	void reduceVideoLikeCount(String videoId);

	/**
	 * @Description: 对视频的评论数量进行累加
	 */
	void addVideoCommentCount(String videoId);

	List<VideosVO> queryVideoList(@Param("id") String id ,@Param("userId") String userId ,
								  @Param("nickname") String nickname, @Param("status") Integer status);
}