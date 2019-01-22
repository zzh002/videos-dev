package com.zzh.controller.interceptor;

import com.zzh.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author ZZH
 * @date 2019/1/22 16:46
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    public RedisOperator redis;
    public static final String ADMIN_SESSION = "Admin-session";

    /**
     * 拦截请求，在controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {

        if(null == request.getSession().getAttribute(ADMIN_SESSION)){
            response.sendRedirect(request.getContextPath()+"/admin/login");

            return false;
        }

        // 放行
        return true;

    }


    /**
     * 请求controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    /**
     * 请求controller之后，视图渲染之后
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }
}
