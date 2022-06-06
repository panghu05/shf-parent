package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名:com.atguigu.config
 *
 */
@SuppressWarnings("unchecked")
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 根据用户到acl_admin表查找用户
        Admin admin = adminService.getByUsername(username);
        //2. 判断admin是否为null
        if (admin == null) {
            //用户名错误
            throw new UsernameNotFoundException("用户名错误");
        }
        //校验密码
        //参数1:用户名
        //参数2:密码
        //参数3:当前用户所拥有的权限集合
        //查询当前用户拥有的权限的code集合
        List<String> permissionCodeList = permissionService.findCodePermissionListByAdminId(admin.getId());

        //创建一个集合用来存储授权的数据
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        if (permissionCodeList != null && permissionCodeList.size() > 0) {
            for (String permissionCode : permissionCodeList) {
                //每一个code就对应一个SimpleGrantedAuthority
                if (permissionCode != null) {
                    //将对象加入集合
                    grantedAuthorityList.add(new SimpleGrantedAuthority(permissionCode));
                }
            }
        }
        return new User(username, admin.getPassword(),
                grantedAuthorityList);
    }
}
