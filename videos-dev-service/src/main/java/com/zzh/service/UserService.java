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
}
