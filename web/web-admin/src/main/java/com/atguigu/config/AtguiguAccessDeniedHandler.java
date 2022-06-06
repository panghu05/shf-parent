package com.atguigu.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 包名:com.atguigu.config
 *
 * @author Leevi
 * 日期2022-05-30  09:33
 */
public class AtguiguAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //当访问被拒绝时，就会执行handle方法，我们在handle方法中去访问/frame/auth.html
        httpServletResponse.sendRedirect("/auth");
    }
}
