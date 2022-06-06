package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 包名:com.atguigu.controller
 *
 */
@Controller
public class IndexController {
    private static final String PAGE_INDEX = "frame/index";
    @Reference
    private PermissionService permissionService;
    @Reference
    private AdminService adminService;

    @RequestMapping("/")
    public String index(Model model){
        //调用业务层的方法查询出动态菜单
        //使用SpringSecurity获取当前登录的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        //从user中获取用户名username
        String username = user.getUsername();
        //根据username查询admin
        Admin admin = adminService.getByUsername(username);

        //将admin存储到请求域
        model.addAttribute("admin",admin);

        List<Permission> permissionList =  permissionService.findMenuPermissionByAdminId(admin.getId());
        //将查询出的动态菜单存到请求域
        model.addAttribute("permissionList",permissionList);
        return PAGE_INDEX;
    }
}
