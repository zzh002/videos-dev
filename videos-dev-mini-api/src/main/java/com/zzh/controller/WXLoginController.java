package com.zzh.controller;

import com.zzh.config.ProjectConfig;
import com.zzh.utils.AesCbcUtil;
import com.zzh.utils.HttpRequest;
import com.zzh.utils.JSONResult;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZZH
 * @date 2019/2/22 15:45
 **/
@RestController
@RequestMapping("/wXLogin")
public class WXLoginController {

    @Autowired
    private ProjectConfig projectConfig;

    @RequestMapping("/decodeUserInfo")
    public JSONResult decodeUserInfo(String encryptedData, String iv, String code) {

//        Map map = new HashMap();

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

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {

                JSONObject userInfoJSON = new JSONObject(result);
//                Map userInfo = new HashMap();
                String nickname = (String) userInfoJSON.get("nickName");
                Integer gender = (Integer) userInfoJSON.get("gender");
                String city = (String) userInfoJSON.get("city");
                String province = (String) userInfoJSON.get("province");
                String country = (String) userInfoJSON.get("country");
                String faceImage = (String) userInfoJSON.get("avatarUrl");

//                userInfo.put("openId", userInfoJSON.get("openId"));
//                userInfo.put("nickName", userInfoJSON.get("nickName"));
//                userInfo.put("gender", userInfoJSON.get("gender"));
//                userInfo.put("city", userInfoJSON.get("city"));
//                userInfo.put("province", userInfoJSON.get("province"));
//                userInfo.put("country", userInfoJSON.get("country"));
//                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
//                // 解密unionId & openId;
//                if (!userInfoJSON.isNull("unionId")) {
//                    userInfo.put("unionId", userInfoJSON.get("unionId"));
//                }
//                map.put("userInfo", userInfo);
            } else {
                return JSONResult.errorMsg("解密失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorException(e.getMessage());
        }
        return JSONResult.ok("解密成功");
    }

}
