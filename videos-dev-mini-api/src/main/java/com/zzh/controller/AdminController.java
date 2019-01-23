package com.zzh.controller;

import com.zzh.config.ProjectUrlConfig;
import com.zzh.enums.VideoStatusEnum;
import com.zzh.pojo.AdminUser;
import com.zzh.pojo.Bgm;
import com.zzh.pojo.Users;
import com.zzh.service.UserService;
import com.zzh.service.VideoService;
import com.zzh.utils.JSONResult;
import com.zzh.utils.PagedResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    ProjectUrlConfig projectUrlConfig;
    @Autowired
    private UserService usersService;
    @Autowired
    private VideoService videoService;

    @RequestMapping("/menu")
    public ModelAndView menu(Map<String, Object> map) {
        return new ModelAndView("center", map);
    }

    @RequestMapping("/login")
    public ModelAndView login(Map<String, Object> map) {
        String url = projectUrlConfig.getUrl();
        map.put("url",url);
        return new ModelAndView("login", map);
    }

    @PostMapping("/loginCheckout")
    @ResponseBody
    public JSONResult loginCheckout(String username, String password,
                                    HttpServletRequest request) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMap("用户名和密码不能为空");
        } else if (username.equals("lee") && password.equals("lee")) {

            String token = UUID.randomUUID().toString();
            AdminUser user = new AdminUser(username, password, token);
            request.getSession().setAttribute("Admin-session", user);
            return JSONResult.ok();
        }

        return JSONResult.errorMsg("登陆失败，请重试...");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(Map<String, Object> map,
                               HttpServletRequest request) {
        request.getSession().removeAttribute("Admin-session");
        String url = projectUrlConfig.getUrl();
        map.put("url",url);
        return new ModelAndView("login", map);
    }

    @GetMapping("/showReportList")
    public ModelAndView showReportList(Map<String, Object> map) {

        return new ModelAndView("video/reportList", map);
    }

    @PostMapping("/reportList")
    @ResponseBody
    public PagedResult reportList(Integer page) {

        PagedResult result = videoService.queryReportList(page, 10);
        return result;
    }

    @PostMapping("/forbidVideo")
    @ResponseBody
    public JSONResult forbidVideo(String videoId) {

        videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
        return JSONResult.ok();
    }

    @PostMapping("/openVideo")
    @ResponseBody
    public JSONResult openVideo(String videoId) {

        videoService.updateVideoStatus(videoId, VideoStatusEnum.SUCCESS.value);
        return JSONResult.ok();
    }

    @GetMapping("/showBgmList")
    public ModelAndView showBgmList(Map<String, Object> map) {
        return new ModelAndView("video/bgmList",map);
    }

    @PostMapping("/queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(Integer page) {
        return videoService.queryBgmList(page, 10);
    }

    @GetMapping("/showAddBgm")
    public ModelAndView showAddBgm(Map<String, Object> map) {
        return new ModelAndView("video/addBgm",map);
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public JSONResult addBgm(Bgm bgm) {

        videoService.addBgm(bgm);
        return JSONResult.ok();
    }

    @PostMapping("/delBgm")
    @ResponseBody
    public JSONResult delBgm(String bgmId) {
        videoService.deleteBgm(bgmId);
        return JSONResult.ok();
    }

    @PostMapping("/bgmUpload")
    @ResponseBody
    public JSONResult bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception {

        // 文件保存的命名空间
//		String fileSpace = File.separator + "imooc_videos_dev" + File.separator + "mvc-bgm";
        String fileSpace = FILE_SPACE + File.separator + "bgm";
        // 保存到数据库中的相对路径
        String uploadPathDB = File.separator + "bgm";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {

                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 文件上传的最终保存路径
                    String finalPath = fileSpace + uploadPathDB + File.separator + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += (File.separator + fileName);

                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
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

        return JSONResult.ok(uploadPathDB);
    }



    @GetMapping("/showUserList")
    public ModelAndView showUserList(Map<String, Object> map) {
        return new ModelAndView("users/usersList",map);
    }


    @PostMapping("/userlist")
    @ResponseBody
    public PagedResult userlist(Users user , Integer page) {

        PagedResult result = usersService.queryUsers(user, page == null ? 1 : page, 10);
        return result;
    }

}
