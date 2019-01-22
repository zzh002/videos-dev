package com.zzh.mapper;

import com.zzh.pojo.Comments;
import com.zzh.pojo.vo.CommentsVO;
import com.zzh.utils.MyMapper;

import java.util.List;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	List<CommentsVO> queryComments(String videoId);
}