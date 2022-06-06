package com.atguigu.service;

import com.atguigu.entity.UserInfo;

/**
 * 包名:com.atguigu.service
 *
 */
public interface UserInfoService {
    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    UserInfo getByPhone(String phone);

    /**
     * 保存用户注册信息
     * @param userInfo
     */
    void insert(UserInfo userInfo);
}
