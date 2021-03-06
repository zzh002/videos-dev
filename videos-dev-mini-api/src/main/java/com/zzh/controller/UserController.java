package com.zzh.controller;

import com.zzh.pojo.Users;
import com.zzh.pojo.UsersReport;
import com.zzh.pojo.vo.PublisherVideo;
import com.zzh.pojo.vo.UsersVO;
import com.zzh.service.UserService;
import com.zzh.utils.JSONResult;
import com.zzh.utils.UploadUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author ZZH
 * @date 2019/1/8 14:44
 **/
@RestController
@RequestMapping("/user")
public class UserController extends BasicController{

    @Autowired
    private UserService userService;

    @PostMapping("/uploadFace")
    public JSONResult uploadFace(String userId,
                                 @RequestParam("file") MultipartFile file) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        // 文件保存的命名空间
        String fileSpace = FILE_SPACE;
        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/face";

        uploadPathDB = UploadUtil.upload(fileSpace,uploadPathDB,file);

        if (uploadPathDB == null || StringUtils.isBlank(uploadPathDB)) {
            return JSONResult.errorMsg("上传出错...");
        }

        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);

        return JSONResult.ok(uploadPathDB);
    }

    @PostMapping("/query")
    public JSONResult query(String userId, String fanId){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        Users userInfo = userService.queryUserInfo(userId);
        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(userInfo, userVO);

        userVO.setFollow(userService.queryIfFollow(userId, fanId));

        return JSONResult.ok(userVO);
    }

    @PostMapping("/queryPublisher")
    public JSONResult queryPublisher(String loginUserId, String videoId,
                                          String publishUserId) {

        if (StringUtils.isBlank(publishUserId)) {
            return JSONResult.errorMsg("");
        }

        // 1. 查询视频发布者的信息
        Users userInfo = userService.queryUserInfo(publishUserId);
        UsersVO publisher = new UsersVO();
        BeanUtils.copyProperties(userInfo, publisher);

        // 2. 查询当前登录者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);

        return JSONResult.ok(bean);
    }

    @PostMapping("/beyourfans")
    public JSONResult beyourfans(String userId, String fanId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return JSONResult.errorMsg("");
        }

        userService.saveUserFanRelation(userId, fanId);

        return JSONResult.ok("关注成功...");
    }

    @PostMapping("/dontbeyourfans")
    public JSONResult dontbeyourfans(String userId, String fanId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return JSONResult.errorMsg("");
        }

        userService.deleteUserFanRelation(userId, fanId);

        return JSONResult.ok("取消关注成功...");
    }

    @PostMapping("/reportUser")
    public JSONResult reportUser(@RequestBody UsersReport usersReport) {

        // 保存举报信息
        userService.reportUser(usersReport);

        return JSONResult.errorMsg("举报成功...有你平台变得更美好...");
    }
}
