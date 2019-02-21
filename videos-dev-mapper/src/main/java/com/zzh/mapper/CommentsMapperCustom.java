package com.zzh.mapper;

import com.zzh.pojo.Comments;
import com.zzh.pojo.vo.CommentDetail;
import com.zzh.pojo.vo.CommentsVO;
import com.zzh.utils.MyMapper;

import java.util.List;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	List<CommentDetail> queryFatherComments(String videoId);

	List<CommentDetail> queryChildComments(String fatherCommentId);

	CommentDetail slectCommentDetailByPrimary(String id);

	void addCommentsLikeCount(String commentId);

	void reduceCommentsLikeCount(String commentId);
}