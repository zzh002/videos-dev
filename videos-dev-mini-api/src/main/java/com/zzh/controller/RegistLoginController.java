package com.zzh.controller;

import com.zzh.pojo.Users;
import com.zzh.pojo.vo.UsersVO;
import com.zzh.service.UserService;
import com.zzh.utils.JSONResult;
import com.zzh.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author ZZH
 * @date 2019/1/7 11:20
 **/
@RestController
public class RegistLoginController extends BasicController {

//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/regist")
//    public JSONResult regist(@RequestBody Users user) throws Exception {
//
//        // 1. 判断用户名和密码必须不为空
//        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
//            return JSONResult.errorMsg("用户名和密码不能为空");
//        }
//
//        // 2. 判断用户名是否存在
//        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
//
//        // 3. 保存用户，注册信息
//        if (!usernameIsExist) {
//            user.setNickname(user.getUsername());
//            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
//            user.setFansCounts(0);
//            user.setReceiveLikeCounts(0);
//            user.setFollowCounts(0);
//            userService.saveUser(user);
//        } else {
//            return JSONResult.errorMsg("用户名已经存在，请换一个再试");
//        }
//
//        user.setPassword("");
//
//        UsersVO userVO = setUserRedisSessionToken(user);
//
//
//        return JSONResult.ok(userVO);
//    }
//
//    @PostMapping("/login")
//    public JSONResult login(@RequestBody Users user) throws Exception {
//        String username = user.getUsername();
//        String password = user.getPassword();
//
////		Thread.sleep(3000);
//
//        // 1. 判断用户名和密码必须不为空
//        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
//            return JSONResult.ok("用户名或密码不能为空...");
//        }
//
//        // 2. 判断用户是否存在
//        Users userResult = userService.queryUserForLogin(username,
//                MD5Utils.getMD5Str(user.getPassword()));
//
//        // 3. 返回
//        if (userResult != null) {
//            userResult.setPassword("");
//            UsersVO userVO = setUserRedisSessionToken(userResult);
//            return JSONResult.ok(userVO);
//        } else {
//            return JSONResult.errorMsg("用户名或密码不正确, 请重试...");
//        }
//    }
//
//    @PostMapping("/logout")
//    public JSONResult logout(String userId){
//        redis.del(USER_REDIS_SESSION + ":" + userId);
//        return JSONResult.ok();
//    }

//    private UsersVO setUserRedisSessionToken(Users userModel) {
//        String uniqueToken = UUID.randomUUID().toString();
//        redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 60 * 30);
//
//        UsersVO userVO = new UsersVO();
//        BeanUtils.copyProperties(userModel, userVO);
//        userVO.setUserToken(uniqueToken);
//        return userVO;
//    }
}
