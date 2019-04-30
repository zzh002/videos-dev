package com.zzh.controller;

import com.zzh.config.ProjectConfig;
import com.zzh.enums.VideoStatusEnum;
import com.zzh.pojo.AdminUsers;
import com.zzh.pojo.Bgm;
import com.zzh.pojo.Users;
import com.zzh.pojo.vo.AdminUsersVO;
import com.zzh.pojo.vo.Reports;
import com.zzh.pojo.vo.VideosVO;
import com.zzh.service.AdminUsersService;
import com.zzh.service.UserService;
import com.zzh.service.VideoService;
import com.zzh.utils.JSONResult;
import com.zzh.utils.PagedResult;
import com.zzh.utils.UploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * @author ZZH
 * @date 2019/1/22 15:14
 **/
@Controller
@RequestMapping("/admin")
public class AdminController extends BasicController {

    @Autowired
    ProjectConfig projectConfig;
    @Autowired
    private UserService usersService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private AdminUsersService adminUsersService;

    /**
     * 后台-主页
     * @param map
     * @return
     */
    @RequestMapping("/menu")
    public ModelAndView menu(Map<String, Object> map) {
        return new ModelAndView("center", map);
    }

    /**
     * 后台-登录
     * @param map
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(Map<String, Object> map) {
        String url = projectConfig.getUrl();
        map.put("url",url);
        return new ModelAndView("login", map);
    }

    /**
     * 后台-检查登陆
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/loginCheckout")
    @ResponseBody
    public JSONResult loginCheckout(String username, String password,
                                    HttpServletRequest request) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMap("用户名和密码不能为空");
        } else {
            AdminUsers adminUsers = adminUsersService.queryAdminUser(username);
            if (adminUsers != null && adminUsers.getPassword().equals(password)) {
                adminUsers.setPassword("");
                AdminUsersVO adminUsersVO = setRedisSessionToken(adminUsers);
                request.getSession().setAttribute(ADMIN_SESSION, adminUsersVO);
                return JSONResult.ok();
            }
            return JSONResult.errorMsg("登陆失败，请重试...");
        }
    }

    /**
     * 后台-注销登录
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(Map<String, Object> map,
                               HttpServletRequest request) {
        AdminUsersVO adminUsersVO = (AdminUsersVO) request.getSession().getAttribute(ADMIN_SESSION);
        request.getSession().removeAttribute(ADMIN_SESSION);
        redis.del(ADMIN_SESSION + ":" + adminUsersVO.getId());
        String url = projectConfig.getUrl();
        map.put("url",url);
        return new ModelAndView("login", map);
    }

    /**
     *后台-举报信息列表界面
     * @param map
     * @return
     */
    @GetMapping("/showReportList")
    public ModelAndView showReportList(Map<String, Object> map) {

        return new ModelAndView("video/reportList", map);
    }

    /**
     * 后台-举报信息列表
     * @param page
     * @return
     */
    @PostMapping("/reportList")
    @ResponseBody
    public PagedResult reportList(Reports reports, Integer page) {

        PagedResult result = videoService.queryReportList(reports, page == null ? 1 : page, 10);
        return result;
    }

    /**
     * 后台-禁播视频
     * @param videoId
     * @return
     */
    @PostMapping("/forbidVideo")
    @ResponseBody
    public JSONResult forbidVideo(String videoId) {

        videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
        return JSONResult.ok();
    }

    /**
     * 后台-解禁视频
     * @param videoId
     * @return
     */
    @PostMapping("/openVideo")
    @ResponseBody
    public JSONResult openVideo(String videoId) {

        videoService.updateVideoStatus(videoId, VideoStatusEnum.SUCCESS.value);
        return JSONResult.ok();
    }

    /**
     * 后台-bgm列表界面
     * @param map
     * @return
     */
    @GetMapping("/showBgmList")
    public ModelAndView showBgmList(Map<String, Object> map) {
        return new ModelAndView("video/bgmList",map);
    }

    /**
     * 后台-查询bgm列表
     * @param page
     * @return
     */
    @PostMapping("/queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(Bgm bgm, Integer page) {
        return videoService.queryBgmList(bgm, page == null ? 1 : page, 10);
    }

    /**
     * 后台-增加bgm界面
     * @param map
     * @return
     */
    @GetMapping("/showAddBgm")
    public ModelAndView showAddBgm(Map<String, Object> map) {
        String url = projectConfig.getUrl();
        map.put("url",url);
        return new ModelAndView("video/addBgm",map);
    }

    /**
     * 后台-增加bgm
     * @param bgm
     * @return
     */
    @PostMapping("/addBgm")
    @ResponseBody
    public JSONResult addBgm(Bgm bgm) {

        videoService.addBgm(bgm);
        return JSONResult.ok();
    }

    /**
     * 后台-删除bgm
     * @param bgmId
     * @return
     */
    @PostMapping("/delBgm")
    @ResponseBody
    public JSONResult delBgm(String bgmId) {
        videoService.deleteBgm(bgmId);
        return JSONResult.ok();
    }

    /**
     * 后台-上传bgm
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/bgmUpload")
    @ResponseBody
    public JSONResult bgmUpload(@RequestParam("file") MultipartFile file) throws Exception {

        // 文件保存的命名空间
        String fileSpace = FILE_SPACE ;
        // 保存到数据库中的相对路径
        String uploadPathDB = "/bgm";

        uploadPathDB = UploadUtil.upload(fileSpace,uploadPathDB,file);

        if (uploadPathDB == null || StringUtils.isBlank(uploadPathDB)) {
            return JSONResult.errorMsg("上传出错...");
        }

        return JSONResult.ok(uploadPathDB);
    }

    /**
     * 后台-用户列表界面
     * @param map
     * @return
     */
    @GetMapping("/showUserList")
    public ModelAndView showUserList(Map<String, Object> map) {
        return new ModelAndView("users/usersList",map);
    }

    /**
     * 后台-视频列表界面
     * @param map
     * @return
     */
    @GetMapping("/showVideoList")
    public ModelAndView showVideoList(Map<String, Object> map) {
        return new ModelAndView("video/videoList",map);
    }

    /**
     * 后台-获取视频列表
     * @param page
     * @return
     */
    @PostMapping("/videoList")
    @ResponseBody
    public PagedResult videoList(VideosVO videosVO , Integer page) {

        PagedResult result = videoService.queryVideoList(videosVO, page == null ? 1 : page, 10);
        return result;
    }

    /**
     * 后台-获取用户列表
     * @param user
     * @param page
     * @return
     */
    @PostMapping("/userlist")
    @ResponseBody
    public PagedResult userlist(Users user , Integer page) {

        PagedResult result = usersService.queryUsers(user, page == null ? 1 : page, 10);
        return result;
    }

    private AdminUsersVO setRedisSessionToken(AdminUsers model) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(ADMIN_SESSION + ":" + model.getId(), uniqueToken, 60 * 60 * 12);

        AdminUsersVO adminUsersVO = new AdminUsersVO();
        BeanUtils.copyProperties(model, adminUsersVO);
        adminUsersVO.setToken(uniqueToken);
        return adminUsersVO;
    }
}
