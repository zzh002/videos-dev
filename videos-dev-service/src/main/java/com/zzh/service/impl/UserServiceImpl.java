package com.zzh.service.impl;

import com.zzh.mapper.UsersMapper;
import com.zzh.pojo.Users;
import com.zzh.service.UserService;
import com.zzh.utils.KeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author ZZH
 * @date 2019/1/7 14:41
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);

        Users result = usersMapper.selectOne(user);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(Users user) {
        String userId = KeyUtils.genUniqueKey();
        if (StringUtils.isBlank(userId)){
            return;
        }
        user.setId(userId);
        usersMapper.insert(user);
    }
}
