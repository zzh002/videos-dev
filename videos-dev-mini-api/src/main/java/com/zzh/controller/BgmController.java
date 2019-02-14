package com.zzh.controller;

import com.zzh.service.BgmService;
import com.zzh.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZZH
 * @date 2019/1/9 10:25
 **/
@RestController
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmService bgmService;

    @PostMapping("/list")
    public JSONResult list() {
        return JSONResult.ok(bgmService.queryBgmList());
    }
}
