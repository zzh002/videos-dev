package com.zzh.service.impl;

import com.zzh.mapper.VideosMapper;
import com.zzh.pojo.Videos;
import com.zzh.service.VideoService;
import com.zzh.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZZH
 * @date 2019/1/17 15:15
 **/
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideosMapper videosMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {

        String id = KeyUtils.genUniqueKey();
        video.setId(id);
        videosMapper.insertSelective(video);

        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(String videoId, String coverPath) {

        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }
}
