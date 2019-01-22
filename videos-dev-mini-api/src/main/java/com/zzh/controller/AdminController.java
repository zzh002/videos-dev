package com.zzh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author ZZH
 * @date 2019/1/22 15:14
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/menu")
    public ModelAndView menu(Map<String, Object> map) {
        return new ModelAndView("center", map);
    }

    @RequestMapping("/login")
    public ModelAndView login(Map<String, Object> map) {
        return new ModelAndView("login", map);
    }

}
