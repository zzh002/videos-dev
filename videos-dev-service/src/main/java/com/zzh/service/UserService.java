package com.zzh.service;

import com.zzh.pojo.Users;

/**
 * @author ZZH
 * @date 2019/1/7 14:37
 **/
public interface UserService {

    //判断用户名是否存在
    boolean queryUsernameIsExist(String username);

    //保存用户
    void saveUser(Users user);

    /**
     * @Description: 用户登录，根据用户名和密码查询用户
     */
    Users queryUserForLogin(String username, String password);

    /**
     * @Description: 用户修改信息
     */
    void updateUserInfo(Users user);

    /**
     * @Description: 查询用户信息
	 */
    Users queryUserInfo(String userId);
}
