package com.zzh.service;

import com.zzh.pojo.Users;
import com.zzh.pojo.UsersReport;
import com.zzh.utils.PagedResult;

/**
 * @author ZZH
 * @date 2019/1/7 14:37
 **/
public interface UserService {


    //保存用户
    Users saveUser(Users user);

    //查询用户（openid）
    Users queryUserFromOpenid(String openid);

    /**
     * @Description: 用户修改信息
     */
    void updateUserInfo(Users user);

    /**
     * @Description: 查询用户信息
	 */
    Users queryUserInfo(String userId);

    /**
     * @Description: 查询用户是否喜欢点赞视频
     */
    boolean isUserLikeVideo(String userId, String videoId);

    /**
     * @Description: 查询用户是否点赞评论
     */
    boolean isUserLikeComments(String userId, String commentId);

    /**
     * @Description: 增加用户和粉丝的关系
     */
    void saveUserFanRelation(String userId, String fanId);

    /**
     * @Description: 删除用户和粉丝的关系
     */
    void deleteUserFanRelation(String userId, String fanId);

    /**
     * @Description: 查询用户是否关注
     */
    boolean queryIfFollow(String userId, String fanId);

    /**
     * @Description: 举报用户
     */
    void reportUser(UsersReport userReport);

    PagedResult queryUsers(Users user, Integer page, Integer pageSize);
}
