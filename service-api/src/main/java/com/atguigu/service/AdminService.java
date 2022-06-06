package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * 包名:com.atguigu.service
 *
 */
public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    Admin getByUsername(String username);
}
