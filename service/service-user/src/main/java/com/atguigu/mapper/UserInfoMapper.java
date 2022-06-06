package com.atguigu.mapper;

import com.atguigu.entity.UserInfo;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface UserInfoMapper {
    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    UserInfo getByPhone(String phone);

    /**
     * 保存用户信息
     * @param userInfo
     */
    void insert(UserInfo userInfo);
}
