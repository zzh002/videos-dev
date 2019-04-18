package com.zzh.controller;

import com.zzh.config.ProjectConfig;
import com.zzh.pojo.Users;
import com.zzh.pojo.vo.UsersVO;
import com.zzh.service.UserService;
import com.zzh.utils.AesCbcUtil;
import com.zzh.utils.HttpRequest;
import com.zzh.utils.JSONResult;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author ZZH
 * @date 2019/2/22 15:45
 **/
@RestController
@RequestMapping("/wXLogin")
public class WXLoginController extends BasicController {

    @Autowired
    private ProjectConfig projectConfig;

    @Autowired
    private UserService userService;

    @RequestMapping("/decodeUserInfo")
    public JSONResult decodeUserInfo(String encryptedData, String iv, String code) {

        // 登录凭证不能为空
        if (code == null || code.length() == 0) {
            return JSONResult.errorException("code 不能为空");
        }

        // 小程序唯一标识 (在微信小程序管理后台获取)
        String wxspAppid = projectConfig.wxspAppid;
        // 小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = projectConfig.wxspSecret;
        // 授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //////////////// ////////////////
        // 请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
                + grant_type;
        // 发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        // 解析相应内容（转换成json对象）
        JSONObject json = new JSONObject(sr);
        // 获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        // 用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        if (StringUtils.isBlank(openid)) {
            return JSONResult.errorMsg("登陆失败！");
        }
        Users user = userService.queryUserFromOpenid(openid);
        if (user == null) {
            user = new Users();
            user.setOpenid(openid);
            user = userService.saveUser(user);
            if (user == null) {
                return JSONResult.errorMsg("登陆失败！");
            }
        }

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject userInfoJSON = new JSONObject(result);
                String nickname = (String) userInfoJSON.get("nickName");
                Integer gender = (Integer) userInfoJSON.get("gender");
                String city = (String) userInfoJSON.get("city");
                String province = (String) userInfoJSON.get("province");
                String country = (String) userInfoJSON.get("country");
                String faceImage = (String) userInfoJSON.get("avatarUrl");
                user.setNickname(nickname);
                user.setGender(gender);
                user.setCity(city);
                user.setProvince(province);
                user.setCountry(country);
                user.setFaceImage(faceImage);
                userService.updateUserInfo(user);
            } else {
                return JSONResult.errorMsg("解密失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorException(e.getMessage());
        }
        UsersVO userVO = setUserRedisSessionToken(user);
        return JSONResult.ok(userVO);
    }

    @PostMapping("/logout")
    public JSONResult logout(String userId){
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return JSONResult.ok();
    }

    private UsersVO setUserRedisSessionToken(Users userModel) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 60 * 60 * 12);

        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(userModel, userVO);
        userVO.setUserToken(uniqueToken);
        return userVO;
    }

}
