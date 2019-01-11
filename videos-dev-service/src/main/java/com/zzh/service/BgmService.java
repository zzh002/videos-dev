package com.zzh.service;

import com.zzh.pojo.Bgm;

import java.util.List;

/**
 * @author ZZH
 * @date 2019/1/9 10:18
 **/
public interface BgmService {

    /**
     * @Description: 查询背景音乐列表
     */
    List<Bgm> queryBgmList();

    /**
     * @Description: 根据id查询bgm信息
     */
    Bgm queryBgmById(String bgmId);
}
