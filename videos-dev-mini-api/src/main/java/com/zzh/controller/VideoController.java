package com.zzh.controller;

import com.zzh.enums.VideoStatusEnum;
import com.zzh.pojo.Bgm;
import com.zzh.pojo.Comments;
import com.zzh.pojo.Users;
import com.zzh.pojo.Videos;
import com.zzh.service.BgmService;
import com.zzh.service.UserService;
import com.zzh.service.VideoService;
import com.zzh.utils.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author ZZH
 * @date 2019/1/9 13:59
 * 视频API
 **/
@RestController
@RequestMapping("/video")
public class VideoController extends BasicController {

    @Autowired
    BgmService bgmService;

    @Autowired
    VideoService videoService;

    @Autowired
    UserService userService;

    /**
     * 上传视频
     * @param userId
     * @param bgmId
     * @param videoSeconds
     * @param videoWidth
     * @param videoHeight
     * @param desc
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value="/upload", headers="content-type=multipart/form-data")
    public JSONResult upload(String userId,
                             String bgmId, double videoSeconds,
                             int videoWidth, int videoHeight,
                             String desc,
                             MultipartFile file) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        // 文件上传的最终保存路径
        String finalVideoPath = "";
        try {
            if (file != null) {

                String fileName = file.getOriginalFilename();
                // abc.mp4
                String arrayFilenameItem[] =  fileName.split("\\.");
                String fileNamePrefix = "";
                for (int i = 0 ; i < arrayFilenameItem.length-1 ; i ++) {
                    fileNamePrefix += arrayFilenameItem[i];
                }
                // fix bug: 解决小程序端OK，PC端不OK的bug，原因：PC端和小程序端对临时视频的命名不同
//                String fileNamePrefix = fileName.split("\\.")[0];
                if (StringUtils.isNotBlank(fileName)) {

                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";

                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }

            } else {
                return JSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        // 判断bgmId是否为空，如果不为空，
        // 那就查询bgm的信息，并且合并视频，生产新的视频
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_SPACE + bgm.getPath();


            //截取上传视频
            ExtractVideo extractVideo = new ExtractVideo(FFMPEG_EXE);
            String videoInputPath = finalVideoPath;
            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            String videoOutputPath = FILE_SPACE + "/" +userId + "/video" + "/" + videoOutputName;
            extractVideo.convertor(videoInputPath,videoOutputPath);

            //视频合并bgm
            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "/" + userId + "/video" + "/" + videoOutputName;
            finalVideoPath = FILE_SPACE + uploadPathDB;
            tool.convertor(videoOutputPath, mp3InputPath, videoSeconds, finalVideoPath);

            //删除中间文件
            DeleteFileUtils.delete(videoOutputPath);
        } else {
            Bgm bgm = new Bgm();
            Users user = userService.queryUserInfo(userId);
            bgm.setAuthor(user.getNickname());
            bgm.setName(user.getNickname() + "创作的原声");
            //截取音频MP3
            ExtractMp3 extractMp3 = new ExtractMp3(FFMPEG_EXE);
            String videoInputPath = finalVideoPath;
            String mp3OutputName = UUID.randomUUID().toString() + ".mp3";
            String mp3OutputPath = FILE_SPACE + "/bgm/" + mp3OutputName;
            extractMp3.convertor(videoInputPath,mp3OutputPath);

            //保存bgm
            bgm.setPath("/bgm/" + mp3OutputName);
            bgmId = videoService.addBgm(bgm);
        }
        System.out.println("uploadPathDB=" + uploadPathDB);
        System.out.println("finalVideoPath=" + finalVideoPath);

       // 对视频进行截图
        FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
        videoInfo.getCover(finalVideoPath, FILE_SPACE + coverPathDB);

        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float)videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        String videoId = videoService.saveVideo(video);

        return JSONResult.ok(videoId);

    }

    /**
     * 上传视频封面
     * @param userId
     * @param videoId
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value="/uploadCover", headers="content-type=multipart/form-data")
    public JSONResult uploadCover(String userId,
                                       String videoId,
                                       MultipartFile file) throws Exception {

        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("视频主键id和用户id不能为空...");
        }

        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";

        uploadPathDB = UploadUtil.upload(FILE_SPACE,uploadPathDB,file);

        if (uploadPathDB == null || StringUtils.isBlank(uploadPathDB)) {
            return JSONResult.errorMsg("上传出错...");
        }

        videoService.updateVideo(videoId, uploadPathDB);

        return JSONResult.ok();
    }

    /**
     *
     * @Description: 分页和搜索查询视频列表
     * isSaveRecord：1 - 需要保存
     * 				 0 - 不需要保存 ，或者为空的时候
     */
    @PostMapping(value="/showAll")
    public JSONResult showAll(@RequestBody Videos video, String loginUserId,
                              Integer isSaveRecord,
                              Integer page,
                              Integer pageSize) {

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedResult result = videoService.getAllVideos(video, isSaveRecord, loginUserId, page, pageSize);
        return JSONResult.ok(result);
    }

    /**
     * @Description: 我关注的人发的视频
     */
    @PostMapping("/showMyFollow")
    public JSONResult showMyFollow(String userId, Integer page) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.ok();
        }

        if (page == null) {
            page = 1;
        }

        int pageSize = 6;

        PagedResult videosList = videoService.queryMyFollowVideos(userId, page, pageSize);

        return JSONResult.ok(videosList);
    }

    /**
     * @Description: 我收藏(点赞)过的视频列表
     */
    @PostMapping("/showMyLike")
    public JSONResult showMyLike(String userId, Integer page, Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.ok();
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 6;
        }

        PagedResult videosList = videoService.queryMyLikeVideos(userId, page, pageSize);

        return JSONResult.ok(videosList);
    }

    /**
     * 热搜查询
     * @return
     */
    @PostMapping(value="/hot")
    public JSONResult hot() {
        return JSONResult.ok(videoService.getHotwords());
    }

    /**
     * 用户喜欢的视频
     * @param userId
     * @param videoId
     * @param videoCreaterId
     * @return
     */
    @PostMapping(value="/userLike")
    public JSONResult userLike(String userId, String videoId, String videoCreaterId) {
        videoService.userLikeVideo(userId, videoId, videoCreaterId);
        return JSONResult.ok();
    }

    /**
     * 用户不喜欢的视频
     * @param userId
     * @param videoId
     * @param videoCreaterId
     * @return
     */
    @PostMapping(value="/userUnLike")
    public JSONResult userUnLike(String userId, String videoId, String videoCreaterId) {
        videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return JSONResult.ok();
    }

    /**
     * 保存评论
     * @param comment
     * @param fatherCommentId
     * @param toUserId
     * @return
     * @throws Exception
     */
    @PostMapping("/saveComment")
    public JSONResult saveComment(@RequestBody Comments comment,
                                       String fatherCommentId, String toUserId) throws Exception {

        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);
        comment.setLikeCounts(Long.valueOf(0));

        videoService.saveComment(comment);
        return JSONResult.ok();
    }

    /**
     * 获取视频评论
     * @param videoId
     * @param loginUserId
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    @PostMapping("/getVideoComments")
    public JSONResult getVideoComments(String videoId, String loginUserId,
                                       Integer page, Integer pageSize)  {

        if (StringUtils.isBlank(videoId)) {
            return JSONResult.ok();
        }

        // 分页查询视频列表，时间顺序倒序排序
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        PagedResult list = videoService.getAllComments(videoId,loginUserId, page, pageSize);

        return JSONResult.ok(list);
    }

    /**
     * 点赞评论
     * @param userId
     * @param commentId
     * @return
     */
    @PostMapping("/likeComment")
    public JSONResult likeComment(String userId, String commentId) {
        videoService.likeComment(userId,commentId);
        return JSONResult.ok();
    }

    /**
     * 取消点赞评论
     * @param userId
     * @param commentId
     * @return
     */
    @PostMapping("/unlikeComment")
    public JSONResult unlikeComment(String userId, String commentId) {
        videoService.unlikeComment(userId,commentId);
        return JSONResult.ok();
    }
}
