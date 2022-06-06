package com.atguigu.interceptor;

import com.alibaba.fastjson.JSON;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 包名:com.atguigu.interceptor
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前是否已登录
        if (request.getSession().getAttribute("USER") == null) {
            //当前未登录
            //响应JSON数据告诉前端:"未登录"，让前段能够跳转到登录
            response.getWriter().write(JSON.toJSONString(Result.build(null, ResultCodeEnum.LOGIN_AUTH)));
            return false;
        }
        return true;
    }
}
