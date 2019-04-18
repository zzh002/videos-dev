package com.zzh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.mapper.*;
import com.zzh.pojo.*;
import com.zzh.service.UserService;
import com.zzh.utils.KeyUtils;
import com.zzh.utils.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * @author ZZH
 * @date 2019/1/7 14:41
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersReportMapper usersReportMapper;

    @Autowired
    private UsersLikeCommentsMapper usersLikeCommentsMapper;



    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users saveUser(Users user) {
        String userId = KeyUtils.genUniqueKey();
        if (StringUtils.isBlank(userId)){
            return null;
        }
        user.setId(userId);
        usersMapper.insert(user);
        return user;
    }

    @Override
    public Users queryUserFromOpenid(String openid) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("openid", openid);
        Users user = usersMapper.selectOneByExample(userExample);
        return user;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(Users user) {

        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        Users user = usersMapper.selectOneByExample(userExample);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return false;
        }

        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);

        if (list != null && list.size() >0) {
            return true;
        }

        return false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikeComments(String userId, String commentId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(commentId)) {
            return false;
        }

        Example example = new Example(UsersLikeComments.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("commentId", commentId);

        List<UsersLikeComments> list = usersLikeCommentsMapper.selectByExample(example);

        if (list != null && list.size() >0) {
            return true;
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserFanRelation(String userId, String fanId) {

        String relId = KeyUtils.genUniqueKey();

        UsersFans userFan = new UsersFans();
        userFan.setId(relId);
        userFan.setUserId(userId);
        userFan.setFanId(fanId);

        usersFansMapper.insert(userFan);

        usersMapper.addFansCount(userId);
        usersMapper.addFollersCount(fanId);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserFanRelation(String userId, String fanId) {

        Example example = new Example(UsersFans.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);

        usersFansMapper.deleteByExample(example);

        usersMapper.reduceFansCount(userId);
        usersMapper.reduceFollersCount(fanId);

    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {

        Example example = new Example(UsersFans.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);

        List<UsersFans> list = usersFansMapper.selectByExample(example);

        if (list != null && !list.isEmpty() && list.size() > 0) {
            return true;
        }

        return false;
    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void reportUser(UsersReport userReport) {

        String urId = KeyUtils.genUniqueKey();
        userReport.setId(urId);
        userReport.setCreateDate(new Date());

        usersReportMapper.insert(userReport);
    }

    @Override
    public PagedResult queryUsers(Users user, Integer page, Integer pageSize) {
        String nickname = "";
        Integer gender = -1;
        String province = "";
        String city = "";
        if (user != null) {
            nickname = user.getNickname();
            if (user.getGender() != null) {
                gender = user.getGender();
            }
            province = user.getProvince();
            city = user.getCity();
        }
        PageHelper.startPage(page, pageSize);

        Example example = new Example(Users.class);
        Example.Criteria userCriteria = example.createCriteria();
        if (StringUtils.isNotBlank(nickname)) {
            userCriteria.andLike("nickname","%" + nickname + "%");
        }
        if (gender > 0) {
            userCriteria.andEqualTo("gender",gender);
        }
        if (StringUtils.isNotBlank(province)) {
            userCriteria.andLike("province","%" + province + "%");
        }
        if (StringUtils.isNotBlank(city)) {
            userCriteria.andLike("city","%" + city + "%");
        }

        List<Users> userList = usersMapper.selectByExample(example);

        PageInfo<Users> pageList = new PageInfo<Users>(userList);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(userList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
