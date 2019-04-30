package com.zzh.service.impl;

import com.zzh.mapper.AdminUsersMapper;
import com.zzh.pojo.AdminUsers;
import com.zzh.service.AdminUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author ZZH
 * @date 2019/4/30 0030 10:55
 **/
@Service
public class AdminUsersServiceImpl implements AdminUsersService {

    @Autowired
    private AdminUsersMapper adminUsersMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public AdminUsers queryAdminUser(String username) {
        Example example = new Example(AdminUsers.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        AdminUsers adminUsers = adminUsersMapper.selectOneByExample(example);
        return adminUsers;
    }
}
