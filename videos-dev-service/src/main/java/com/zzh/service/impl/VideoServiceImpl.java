package com.zzh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.enums.BGMOperatorTypeEnum;
import com.zzh.mapper.*;
import com.zzh.pojo.*;
import com.zzh.pojo.vo.CommentDetail;
import com.zzh.pojo.vo.CommentsVO;
import com.zzh.pojo.vo.Reports;
import com.zzh.pojo.vo.VideosVO;
import com.zzh.service.BgmService;
import com.zzh.service.UserService;
import com.zzh.service.VideoService;
import com.zzh.utils.KeyUtils;
import com.zzh.utils.PagedResult;
import com.zzh.utils.TimeAgoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;


import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author ZZH
 * @date 2019/1/17 15:15
 **/
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CommentsMapperCustom commentsMapperCustom;

    @Autowired
    private UsersReportMapperCustom usersReportMapperCustom;

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private UsersLikeCommentsMapper usersLikeCommentsMapper;

    @Autowired
    private UserService userService;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {

        String id = KeyUtils.genUniqueKey();
        video.setId(id);
        videosMapper.insertSelective(video);

        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(String videoId, String coverPath) {

        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord, String loginUserId,
                                    Integer page, Integer pageSize) {

        // 保存热搜词
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords record = new SearchRecords();
            String recordId = KeyUtils.genUniqueKey();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryAllVideos(desc,userId);
        //查询登录用户是否点赞该视频
        for (VideosVO videosVO : list) {
            Bgm bgm = bgmMapper.selectByPrimaryKey(videosVO.getAudioId());
            if (bgm != null) {
                videosVO.setAuthor(bgm.getAuthor());
                videosVO.setName(bgm.getName());
            }
            videosVO.setIsPraise(userService.isUserLikeVideo(loginUserId, videosVO.getId()));
        }

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotwords() {
        return searchRecordsMapper.getHotwords();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 1. 保存用户和视频的喜欢点赞关联关系表
        String likeId = KeyUtils.genUniqueKey();
        UsersLikeVideos ulv = new UsersLikeVideos();
        ulv.setId(likeId);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        usersLikeVideosMapper.insert(ulv);

        // 2. 视频喜欢数量累加
        videosMapperCustom.addVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累加
        usersMapper.addReceiveLikeCount(videoCreaterId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 1. 删除用户和视频的喜欢点赞关联关系表

        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        usersLikeVideosMapper.deleteByExample(example);

        // 2. 视频喜欢数量累减
        videosMapperCustom.reduceVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累减
        usersMapper.reduceReceiveLikeCount(videoCreaterId);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        //保存评论
        String id = KeyUtils.genUniqueKey();
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentsMapper.insert(comment);
        //修改视频的评论数
        if (comment.getFatherCommentId().equals("undefined")) {
            videosMapperCustom.addVideoCommentCount(comment.getVideoId());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void likeComment(String userId, String commentId) {
        //判断是否存在点赞关系
        UsersLikeComments ulc = new UsersLikeComments();
        ulc.setUserId(userId);
        ulc.setCommentId(commentId);
        int count  = usersLikeCommentsMapper.selectCount(ulc);
        if (count > 0) {
            return;
        }
        //添加点赞关系
        String id = KeyUtils.genUniqueKey();
        ulc.setId(id);
        usersLikeCommentsMapper.insert(ulc);
        //累加点赞数量
        commentsMapperCustom.addCommentsLikeCount(commentId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void unlikeComment(String userId, String commentId) {
        // 1. 删除用户和评论的喜欢点赞关联关系表

        Example example = new Example(UsersLikeComments.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("commentId", commentId);

        usersLikeCommentsMapper.deleteByExample(example);
        //累加点赞数量
        commentsMapperCustom.reduceCommentsLikeCount(commentId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllComments(String videoId, String loginUserId, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        List<CommentDetail> list = commentsMapperCustom.queryFatherComments(videoId);

        List<CommentsVO> commentsVOList = new ArrayList<>();

        for (CommentDetail c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
            CommentsVO commentsVO = new CommentsVO();
            BeanUtils.copyProperties(c, commentsVO);
            commentsVO.setIsPraise(userService.isUserLikeComments(loginUserId,commentsVO.getId()));
            Set<CommentDetail> commentSet = new HashSet<>();
            findChildComments(commentSet,commentsVO.getId());
            List<CommentDetail> commentDetailList = new ArrayList<>();
            if (commentSet != null) {
                for (CommentDetail commentDetail : commentSet) {
                    if (!c.getId().equals(commentDetail.getId())) {
                        commentDetail.setIsPraise(userService.isUserLikeComments(loginUserId,commentDetail.getId()));
                        commentDetailList.add(commentDetail);
                    }
                }
            }
            commentsVO.setCommentDetailList(commentDetailList);
            commentsVOList.add(commentsVO);
        }

        PageInfo<CommentDetail> pageList = new PageInfo<>(list);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(commentsVOList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    //递归算法,算出子节点
    private Set<CommentDetail> findChildComments(Set<CommentDetail> commentSet ,String commentId){
        CommentDetail commentDetail = commentsMapperCustom.slectCommentDetailByPrimary(commentId);
        if(commentDetail != null){
            String timeAgo = TimeAgoUtils.format(commentDetail.getCreateTime());
            commentDetail.setTimeAgoStr(timeAgo);
            commentSet.add(commentDetail);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<CommentDetail> commentDetailList = commentsMapperCustom.queryChildComments(commentId);
        for(CommentDetail item : commentDetailList){
            findChildComments(commentSet,item.getId());
        }
        return commentSet;
    }

    @Override
    public PagedResult queryReportList(Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        List<Reports> reportsList = usersReportMapperCustom.selectAllVideoReport();

        PageInfo<Reports> pageList = new PageInfo<Reports>(reportsList);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(reportsList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    @Override
    public void updateVideoStatus(String videoId, Integer status) {

        Videos video = new Videos();
        video.setId(videoId);
        video.setStatus(status);
        videosMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        Example example = new Example(Bgm.class);
        List<Bgm> list = bgmMapper.selectByExample(example);

        PageInfo<Bgm> pageList = new PageInfo<>(list);

        PagedResult result = new PagedResult();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());

        return result;
    }

    @Override
    public PagedResult queryVideoList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        Example example = new Example(Bgm.class);
        List<Videos> list = videosMapper.selectByExample(example);

        PageInfo<Videos> pageList = new PageInfo<>(list);

        PagedResult result = new PagedResult();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());

        return result;
    }

    @Override
    public String addBgm(Bgm bgm) {
        String bgmId = KeyUtils.genUniqueKey();
        bgm.setId(bgmId);
        bgmMapper.insert(bgm);
        return bgmId;

//        Map<String, String> map = new HashMap<>();
//        map.put("operType", BGMOperatorTypeEnum.ADD.type);
//        map.put("path", bgm.getPath());

//        zkCurator.sendBgmOperator(bgmId, JsonUtils.objectToJson(map));
    }

    @Override
    public void deleteBgm(String id) {
        Bgm bgm = bgmMapper.selectByPrimaryKey(id);

        bgmMapper.deleteByPrimaryKey(id);

        Map<String, String> map = new HashMap<>();
        map.put("operType", BGMOperatorTypeEnum.DELETE.type);
        map.put("path", bgm.getPath());

//        zkCurator.sendBgmOperator(id, JsonUtils.objectToJson(map));

    }
}
